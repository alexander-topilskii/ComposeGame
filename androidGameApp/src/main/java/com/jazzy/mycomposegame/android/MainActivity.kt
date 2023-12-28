package com.jazzy.mycomposegame.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.jazzy.mycomposegame.App
import com.jazzy.mycomposegame.database.MemoryTodoDatabase
import com.jazzy.mycomposegame.ui.GameDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                App(
                    DefaultStoreFactory(),
                    MemoryTodoDatabase(),
                    DefaultDispatchers
                )
            }
        }
    }
}

object DefaultDispatchers : GameDispatchers {

    override val main: CoroutineDispatcher get() = Dispatchers.Main.immediate
    override val io: CoroutineDispatcher get() = Dispatchers.IO
    override val unconfined: CoroutineDispatcher get() = Dispatchers.Unconfined
}
