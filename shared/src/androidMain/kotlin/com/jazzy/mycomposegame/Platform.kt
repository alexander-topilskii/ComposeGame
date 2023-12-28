package com.jazzy.mycomposegame

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

@Composable
actual fun getScreenSize(): Pair<Int, Int> {
    val configuration = LocalConfiguration.current
    val screenWidth: Int = configuration.screenWidthDp
    val screenHeight: Int = configuration.screenHeightDp
    return screenWidth to screenHeight;
}

actual fun getCurrentThread(): String = "not implemented"
