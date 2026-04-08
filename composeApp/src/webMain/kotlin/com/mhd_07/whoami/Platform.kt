package com.mhd_07.whoami

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform