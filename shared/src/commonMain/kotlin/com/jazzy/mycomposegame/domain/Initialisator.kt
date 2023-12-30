package com.jazzy.mycomposegame.domain

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.jazzy.mycomposegame.domain.data.BallData
import com.jazzy.mycomposegame.domain.data.PlayerData
import com.jazzy.mycomposegame.domain.data.PointF
import com.jazzy.mycomposegame.domain.data.RectangleData
import com.jazzy.mycomposegame.random
import com.jazzy.mycomposegame.ui.GameStoreFactory

internal object Initialisator {

    suspend fun init(width: Dp, height: Dp, onMsg: suspend (GameStoreFactory.Msg) -> Unit) {
        initScreenSize(onMsg, width, height)
        initPlayer(onMsg)
        initBalls(onMsg)
        initFloor(onMsg)
    }

    private suspend fun initFloor(onMsg: suspend (GameStoreFactory.Msg) -> Unit) {
        val rectangle = RectangleData(
            size = 1000f,
            sizeY = 25f,
            color = Color.Green,
            position = PointF(-10f, 100f),
            speed = random(0, 8) + 16f,
            angle = random(0, 15) + 30f
        )

        onMsg(GameStoreFactory.Msg.BackgroundUnitCreated(rectangle))
    }

    private suspend fun initScreenSize(
        onMsg: suspend (GameStoreFactory.Msg) -> Unit,
        width: Dp,
        height: Dp
    ) {
        onMsg(GameStoreFactory.Msg.ChangeScreenParams(width = width, height = height))
    }

    private suspend fun initPlayer(onMsg: suspend (GameStoreFactory.Msg) -> Unit) {
        onMsg(
            GameStoreFactory.Msg.PlayerCreated(
                PlayerData(
                    size = 20f,
                    color = Color.Red,
                    position = PointF(10f, 10f),
                    speed = 0f,
                    angle = 0f
                )
            )
        )
    }

    private suspend fun initBalls(onMsg: suspend (GameStoreFactory.Msg) -> Unit) {
        repeat(0) {
            val ball = BallData(
                size = random(25, 45),
                color = Color.Blue,
                position = PointF(random(0f, 1000f), random(0f, 1000f)),
                speed = random(0, 8) + 16f,
                angle = random(0, 15) + 30f
            )

            onMsg(GameStoreFactory.Msg.GameUnitCreated(ball))
        }
    }
}