package com.jazzy.mycomposegame

import com.jazzy.mycomposegame.ui.GameDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object DesktopDispatchers : GameDispatchers {

    override val main: CoroutineDispatcher get() = Dispatchers.IO
    override val io: CoroutineDispatcher get() = Dispatchers.IO
    override val unconfined: CoroutineDispatcher get() = Dispatchers.Unconfined
}