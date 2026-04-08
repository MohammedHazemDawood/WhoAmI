package com.mhd_07.whoami.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.browser.window
import org.w3c.dom.events.Event

@Composable
actual fun rememberScreenType(): State<ScreenInfo> {
    val screenInfo = remember { mutableStateOf(calculateScreenInfo()) }

    DisposableEffect(Unit) {
        window.requestAnimationFrame {
            screenInfo.value = calculateScreenInfo()
        }
        val listener: (Event) -> Unit = {
            screenInfo.value = calculateScreenInfo()
        }
        window.addEventListener("resize", listener)
        onDispose {
            window.removeEventListener("resize", listener)
        }
    }
    return screenInfo
}

object Breakpoints {
    const val MOBILE_MAX = 767
    const val TABLET_MAX = 1024
    const val DESKTOP_MAX = 1440
}

fun calculateScreenInfo(): ScreenInfo {
    val width = window.innerWidth
    val height = window.innerHeight

    val agent = window.navigator.userAgent

    val isMob = listOf(
        "mobi",
        "android",
        "iphone",
        "ipad",
        "ipod",
        "blackberry",
        "windows phone"
    ).any { agent.contains(it) }

    return when {
        (isMob && width <= Breakpoints.MOBILE_MAX) -> {
            ScreenInfo(width, height, ScreenType.PHONE)
        }

        (width <= Breakpoints.TABLET_MAX) -> {
            ScreenInfo(
                width,
                height,
                ScreenType.PHONE/* there is no difference between mobile and tablet for my ui */
            )
        }

        else -> ScreenInfo(width, height, if (isMob) ScreenType.PHONE else ScreenType.DESKTOP)
    }
}