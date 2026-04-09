package com.mhd_07.whoami

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentDataType.Companion.Date
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mhd_07.whoami.theme.ScreenType
import com.mhd_07.whoami.theme.WhoAmITheme
import com.mhd_07.whoami.theme.rememberScreenInfo
import kotlinx.datetime.Month

@Composable
fun App() {
    val screenInfo by rememberScreenInfo()
    WhoAmITheme {
        SelectionContainer {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.background
            ) {
                var id by remember { mutableStateOf("") }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(if (screenInfo.type == ScreenType.DESKTOP) 88.dp else 48.dp)
                ) {
                    item(key = "#header") {
                        Column(
                            modifier = Modifier.fillMaxWidth(0.8f)
                                .padding(if (screenInfo.type == ScreenType.DESKTOP) 32.dp else 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(if (screenInfo.type == ScreenType.DESKTOP) 8.dp else 4.dp)
                        ) {
                            Text(
                                text = "Who Am I",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Discover your identity through your national ID",
                                style = MaterialTheme.typography.displayLarge,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                    item(key = "#input") {
                        Card(
                            modifier = Modifier.fillMaxWidth(0.8f).fillParentMaxHeight(0.4f),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                BasicTextField(
                                    value = id,
                                    onValueChange = { s ->
                                        s.filter { it.isDigit() }
                                            .let { if (it.length <= 14) id = it }
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    visualTransformation = GroupedVisualTransformer(
                                        //X - YY - MM - DD - SS - CCC - G - R
                                        groups = listOf(
                                            1,
                                            2,
                                            2,
                                            2,
                                            2,
                                            3,
                                            1,
                                            1
                                        ), //1, 2, 2, 2, 2 , 3, 1, 1 sum is = 14
                                        separator = " "
                                    ),
                                    textStyle = MaterialTheme.typography.displayLarge.copy(
                                        color = MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center
                                    ),
                                    decorationBox = {
                                        Box(
                                            contentAlignment = Alignment.CenterStart,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            if (id.isEmpty())
                                                Text(
                                                    text = "Enter your ID",
                                                    style = MaterialTheme.typography.displayLarge,
                                                    color = MaterialTheme.colorScheme.onSurface.copy(
                                                        alpha = 0.5f
                                                    ),
                                                    modifier = Modifier.fillMaxWidth(),
                                                    textAlign = TextAlign.Center
                                                )
                                            Box(
                                                modifier = Modifier.fillMaxWidth(),
                                                contentAlignment = Alignment.Center
                                            ) { it() }
                                        }
                                    }
                                )
                            }
                        }
                    }
                    item(key = "#results") {
                        Row(
                            modifier = Modifier.fillMaxWidth(0.8f),
                            horizontalArrangement = Arrangement.spacedBy(if (screenInfo.type == ScreenType.DESKTOP) 8.dp else 4.dp)
                        ) {
                            val result = remember { derivedStateOf { IdAnalyzer().analyze(id) } }
                            if (result.value is Result.Error)
                                Box(
                                    modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = (result.value as Result.Error).message,
                                        style = MaterialTheme.typography.displayLarge,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            else {
                                Card(
                                    modifier = Modifier.weight(1f).fillParentMaxHeight(0.4f),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize()
                                            .padding(if (screenInfo.type == ScreenType.DESKTOP) 32.dp else 16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "Birth Date",
                                            style = MaterialTheme.typography.titleSmall,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                        Text(
                                            text = (result.value as Result.Success).let {
                                                it.idData.day.toString() + " " + getMonthNameKmp(
                                                    it.idData.month
                                                ) + ", " + it.idData.year.toString()
                                            },
                                            style = MaterialTheme.typography.displayLarge,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                    }
                                }


                                Card(
                                    modifier = Modifier.weight(1f).fillParentMaxHeight(0.4f),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize()
                                            .padding(if (screenInfo.type == ScreenType.DESKTOP) 32.dp else 16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "Birth Place",
                                            style = MaterialTheme.typography.titleSmall,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                        Text(
                                            text = (result.value as Result.Success).idData.birthPlace.name,
                                            style = MaterialTheme.typography.displayLarge,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                    }
                                }


                                Card(
                                    modifier = Modifier.weight(1f).fillParentMaxHeight(0.4f),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize()
                                            .padding(if (screenInfo.type == ScreenType.DESKTOP) 32.dp else 16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "Gender",
                                            style = MaterialTheme.typography.titleSmall,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                        Text(
                                            text = (result.value as Result.Success).idData.gender.name,
                                            style = MaterialTheme.typography.displayLarge,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                    }
                                }
                            }
                        }
                    }
                    item(key = "#footer") {
                        Text(
                            text = "Be Sure we don't collect your data, You can check code on github",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }
}

fun getMonthNameKmp(monthNumber: Int): String {
    // Returns the enum constant name (e.g., "JANUARY")
    return Month(monthNumber).name.lowercase()
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}
