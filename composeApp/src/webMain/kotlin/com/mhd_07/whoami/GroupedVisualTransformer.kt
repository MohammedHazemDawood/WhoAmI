package com.mhd_07.whoami

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class GroupedVisualTransformer(
    val groups: List<Int>,
    val separator: String
) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val textCopy = text.text
        val newText = buildString {
            var index = 0
            for ((i, size) in groups.withIndex()) {
                repeat(size) {
                    if (index < textCopy.length)
                        append(textCopy[index++])
                }
                if (index == groups.take(i + 1).sum() && i < groups.size - 1)
                    append(separator)
            }
        }
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var transformed = offset
                var sum = 0

                for (i in groups.indices) {
                    sum += groups[i]
                    if (offset > sum && i < groups.lastIndex) {
                        transformed += separator.length
                    }
                }

                return transformed.coerceAtMost(newText.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                var original = offset
                var sum = 0
                var transformedSum = 0

                for (i in groups.indices) {
                    sum += groups[i]
                    transformedSum += groups[i]

                    if (i < groups.lastIndex) {
                        transformedSum += separator.length
                    }

                    if (offset > transformedSum) {
                        original -= separator.length
                    }
                }

                return original.coerceIn(0, text.text.length)
            }
        }

        return TransformedText(AnnotatedString(newText), offsetMapping)
    }
}