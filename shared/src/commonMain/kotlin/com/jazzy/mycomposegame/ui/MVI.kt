package com.jazzy.mycomposegame.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.jazzy.mycomposegame.database.GameDatabase
import com.jazzy.mycomposegame.ui.display.display

@Composable
fun MainGame(
    storeFactory: StoreFactory,
    database: GameDatabase<Any>,
    dispatchers: GameDispatchers,
) {
    val controller = GameArchElementsController(
        storeFactory = storeFactory,
        database = database,
        dispatchers = dispatchers
    )

    val states = controller.state.collectAsState(null)

    Column {
        states.value?.changeableTextUi?.display(controller)

        Button(
            onClick = { controller.onButtonClicked() }
        ) {
            Text("Submit")
        }
    }
}
