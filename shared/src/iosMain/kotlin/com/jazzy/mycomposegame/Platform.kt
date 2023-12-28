package com.jazzy.mycomposegame

import androidx.compose.runtime.Composable
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

@Composable
actual fun getScreenSize():  Pair<Int, Int> {
    return 1 to 1;
}

actual fun getCurrentThread(): String = "not implemented"
