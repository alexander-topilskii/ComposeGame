package com.jazzy.mycomposegame.domain

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.jazzy.mycomposegame.domain.data.BallData
import com.jazzy.mycomposegame.domain.data.BoxData
import com.jazzy.mycomposegame.domain.data.PlayerData
import com.jazzy.mycomposegame.domain.data.PointF
import com.jazzy.mycomposegame.random
import com.jazzy.mycomposegame.ui.GameStoreFactory

internal object Initialisator {

    suspend fun init(width: Dp, height: Dp, onMsg: suspend (GameStoreFactory.Msg) -> Unit) {
        onMsg(GameStoreFactory.Msg.ChangeScreenParams(width = width, height = height))

        onMsg(
            GameStoreFactory.Msg.PlayerUpdated(
                PlayerData(
                    size = 20f,
                    color = Color.Red,
                    position = PointF(10f, 10f),
                    speed = 0f,
                    angle = 0f
                )
            )
        )

        repeat(3) {
            val ball = BallData(
                size = random(25, 45),
                color = Color.Blue,
                position = PointF(random(0f, 1000f), random(0f, 1000f)),
                speed = random(0, 8) + 16f,
                angle = random(0, 15) + 30f
            )

            onMsg(GameStoreFactory.Msg.GameUnitCreated(ball))
        }

        val box = BoxData(
            size = random(25, 45),
            color = Color.Green,
            position = PointF(random(0f, 100f), random(0f, 100f)),
            speed = random(0, 8) + 16f,
            angle = random(0, 15) + 30f
        )

        onMsg(GameStoreFactory.Msg.BackgroundUnitCreated(box))
    }
}