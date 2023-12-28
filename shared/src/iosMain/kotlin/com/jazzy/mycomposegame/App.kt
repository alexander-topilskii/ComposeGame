package com.jazzy.mycomposegame

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.jazzy.mycomposegame.database.MemoryTodoDatabase
import com.jazzy.mycomposegame.ui.GameDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

fun MainViewController() = ComposeUIViewController {
    App(
        storeFactory = DefaultStoreFactory(),
        database = MemoryTodoDatabase(),
        dispatchers = DefaultDispatchers
    )
}

object DefaultDispatchers : GameDispatchers {

    override val main: CoroutineDispatcher get() = Dispatchers.Main.immediate
    override val io: CoroutineDispatcher get() = Dispatchers.Main.immediate
    override val unconfined: CoroutineDispatcher get() = Dispatchers.Unconfined
}