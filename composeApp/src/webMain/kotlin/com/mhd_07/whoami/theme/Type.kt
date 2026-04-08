package com.mhd_07.whoami.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import whoami.composeapp.generated.resources.Res
import whoami.composeapp.generated.resources.mango_bold
import whoami.composeapp.generated.resources.mango_extra_light
import whoami.composeapp.generated.resources.mango_light
import whoami.composeapp.generated.resources.mango_regular

@Composable
fun GetMangoFont() =
    FontFamily(
        Font(Res.font.mango_regular, FontWeight.Normal),
        Font(Res.font.mango_bold, FontWeight.Bold),
        Font(Res.font.mango_light, FontWeight.Light),
        Font(Res.font.mango_extra_light, FontWeight.ExtraLight)
    )

sealed class FontSize {
    abstract val titleLarge: TextUnit
    abstract val description: TextUnit
    abstract val titleSmall: TextUnit
    abstract val display: TextUnit

    data object Desktop : FontSize() {
        override val titleLarge = 40.sp
        override val titleSmall = 20.sp
        override val description = 18.sp
        override val display = 24.sp
    }

    object Mobile : FontSize() {
        override val titleLarge = 24.sp
        override val description = 16.sp
        override val titleSmall = 18.sp
        override val display = 20.sp
    }
}

@Composable
fun whoamiType(): Typography {
    val screenInfo = rememberScreenType()
    val fontSizes by remember {
        derivedStateOf {
            when (screenInfo.value.type) {
                ScreenType.DESKTOP -> FontSize.Desktop
                ScreenType.PHONE -> FontSize.Mobile
            }
        }
    }
    return Typography(
        displayLarge = TextStyle(
            fontFamily = GetMangoFont(),
            fontWeight = FontWeight.Normal,
            fontSize = fontSizes.display
        ),
        titleLarge = TextStyle(
            fontFamily = GetMangoFont(),
            fontWeight = FontWeight.Bold,
            fontSize = fontSizes.titleLarge
        ),
        titleSmall = TextStyle(
            fontFamily = GetMangoFont(),
            fontWeight = FontWeight.Normal,
            fontSize = fontSizes.titleSmall
        ),
        bodySmall = TextStyle(
            fontFamily = GetMangoFont(),
            fontWeight = FontWeight.Thin,
            fontSize = fontSizes.description
        ),
    )
}

data class ScreenInfo(
    val width: Int,
    val height: Int,
    val type: ScreenType
)

@Composable
expect fun rememberScreenType(): State<ScreenInfo>

enum class ScreenType {
    DESKTOP,
    PHONE
}