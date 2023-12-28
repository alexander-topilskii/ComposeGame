package com.jazzy.mycomposegame

import androidx.compose.runtime.Composable
import java.awt.Toolkit

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

@Composable
actual fun getScreenSize():  Pair<Int, Int> {
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    val screenWidth = screenSize.width
    val screenHeight = screenSize.height
    return screenWidth to screenHeight
}