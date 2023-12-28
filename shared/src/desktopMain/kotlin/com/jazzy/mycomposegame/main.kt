package com.jazzy.mycomposegame

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.jazzy.mycomposegame.database.MemoryTodoDatabase

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "KMMGameOnCompose") {
        App(
            storeFactory = DefaultStoreFactory(),
            database = MemoryTodoDatabase(),
            dispatchers = DesktopDispatchers
        )
    }
}

@Preview
@Composable
fun AppDesktopPreview() {
//    App()
}
