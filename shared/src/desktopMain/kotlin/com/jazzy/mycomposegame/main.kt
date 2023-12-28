package com.jazzy.mycomposegame

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.timetravel.server.TimeTravelServer
import com.arkivanov.mvikotlin.timetravel.store.TimeTravelStoreFactory
import com.jazzy.mycomposegame.database.MemoryTodoDatabase
import javax.swing.SwingUtilities

fun main() = application {
    val timeTravelServer = TimeTravelServer(runOnMainThread = { SwingUtilities.invokeLater(it) })
    timeTravelServer.start()

    Window(onCloseRequest = ::exitApplication, title = "KMMGameOnCompose") {
        App(
            storeFactory = LoggingStoreFactory(TimeTravelStoreFactory()),
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


