package com.jazzy.mycomposegame

import androidx.compose.runtime.Composable

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

@Composable
expect fun getScreenSize(): Pair<Int, Int>

expect fun getCurrentThread() :String
