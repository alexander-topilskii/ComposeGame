package com.jazzy.mycomposegame

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun MainGame() {
    val game = remember { Game() }
    val density = LocalDensity.current
    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos {
                game.update(it)
            }
        }
    }

    Column(modifier = Modifier
        .background(Color(51, 153, 255))
        .fillMaxHeight()
        .fillMaxWidth()
        .onKeyEvent { keyEvent: KeyEvent ->
            game.onKeyPressed(keyEvent)
            return@onKeyEvent false
        }

    ) {
        Row(modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth()) {
            Button({
                if (game.gameState == GameState.STOPPED)
                    game.startGame()
                else
                    game.endGame()
            }) {
                Text(if(game.gameState == GameState.STOPPED) "Play" else "Stop")
            }
            Text(
                game.gameStatus,
                modifier = Modifier.align(Alignment.CenterVertically).padding(horizontal = 16.dp),
                color = Color.White
            )

            Text(
                "${(game.totalTime / 1E8).roundToInt() / 10f} seconds.",
                modifier = Modifier.align(Alignment.CenterVertically).padding(horizontal = 16.dp),
                color = Color.White
            )
        }


        Box(modifier = Modifier
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
                        game.width = it.width.toDp()
                        game.height = it.height.toDp()
                    }
                }) {
                game.gameObjects.forEach {
                    when (it) {
                        is BallData -> Ball(it)
                        is BoxData -> Box(it)
                        is PlayerData -> Player(it)
                    }
                }
            }

        }

    }
}