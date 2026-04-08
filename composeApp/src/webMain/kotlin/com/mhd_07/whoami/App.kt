package com.mhd_07.whoami

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mhd_07.whoami.theme.WhoAmITheme
import com.mhd_07.whoami.theme.rememberScreenType

@Composable
fun App() {
    WhoAmITheme {
        val screenType by rememberScreenType()
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item(key = "#hero") {

            }
        }
    }
}