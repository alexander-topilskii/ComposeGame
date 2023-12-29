package com.jazzy.mycomposegame.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.jazzy.mycomposegame.Box
import com.jazzy.mycomposegame.BoxData
import com.jazzy.mycomposegame.GameUnitBallData
import com.jazzy.mycomposegame.GameUnitBallDisplay
import com.jazzy.mycomposegame.Player
import com.jazzy.mycomposegame.PlayerData
import com.jazzy.mycomposegame.database.GameDatabase
import com.jazzy.mycomposegame.getScreenSize
import com.jazzy.mycomposegame.ui.display.display

@Composable
fun MainGame(
    storeFactory: StoreFactory,
    database: GameDatabase<Any>,
    dispatchers: GameDispatchers,
) {
    val density = LocalDensity.current
    val screenSize = getScreenSize()

    val controller = remember {
        GameArchElementsController(
            storeFactory = storeFactory,
            database = database,
            screenSize = with(density) { screenSize.first.toDp() to screenSize.second.toDp() },
            dispatchers = dispatchers
        )
    }

    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos { time ->
                controller.onTimerUpdated(time)
            }
        }
    }

    val states = controller.state.collectAsState(null)

    Column {
        states.value?.text?.display(controller)

        Button(
            onClick = { controller.onButtonClicked() }
        ) {
            Text("Submit")
        }

        Box(
            modifier = Modifier
                .background(Color.DarkGray)
                .padding(50.dp)
                .border(BorderStroke(2.dp, Color.Red))
                .fillMaxWidth()
                .fillMaxHeight()
                .clipToBounds()
        ) {

            Box(modifier = Modifier
                .background(Color.Black)
                .fillMaxWidth()
                .fillMaxHeight()
                .clipToBounds()
                .onSizeChanged {
                    with(density) {
                        controller.onDensityChanged(
                            it.width.toDp(),
                            it.height.toDp()
                        )
                    }
                }) {
                states.value?.gameObjects?.forEach { gameObject ->
                    when (gameObject) {
                        is BoxData -> Box(gameObject)
                        is PlayerData -> Player(gameObject)
                    }
                }
                states.value?.gameUnits?.forEach { gameUnit ->
                    when (gameUnit) {
                        is GameUnitBallData -> GameUnitBallDisplay(gameUnit)
                    }
                }
            }
        }
    }
}


