package com.jazzy.mycomposegame.domain

import androidx.compose.ui.unit.dp
import com.jazzy.mycomposegame.domain.data.BallData
import com.jazzy.mycomposegame.domain.data.GameUnit
import com.jazzy.mycomposegame.domain.data.PlayerData
import com.jazzy.mycomposegame.domain.data.PointF
import com.jazzy.mycomposegame.domain.data.angle
import com.jazzy.mycomposegame.domain.data.length
import com.jazzy.mycomposegame.domain.mvi.ScreenSize
import com.jazzy.mycomposegame.ui.GameStoreFactory

object GameUnitUpdater {

    suspend fun getUpdatedUnits(
        dt: Float,
        gameUnits: List<GameUnit>,
        screenSize: ScreenSize,
        onMsg: suspend (GameStoreFactory.Msg) -> Unit
    ) {
        val newGameUnits = gameUnits.map { gu ->
            return@map when (gu) {
                is BallData -> gu.getUpdatedBallData(dt, screenSize)
                else -> gu
            }
        }

        if (newGameUnits.isNotEmpty()) {
            onMsg.invoke(GameStoreFactory.Msg.GameUnitsUpdated(newGameUnits))
        }
    }


    private fun BallData.getUpdatedBallData(
        floatDelta: Float,
        screenSize: ScreenSize
    ): GameUnit {
        if (!isEnabled) return this

        val velocity = movementVector.times(floatDelta)
        val newPosition = this.position.plus(velocity)

        var newSpeed = this.speed
        var newAngle = this.angle
        var newPosX = newPosition.x
        var newPosY = newPosition.y

        val xOffset = newPosition.x.dp
        val yOffset = newPosition.y.dp

        if (xOffset > screenSize.width - size.dp || xOffset < 0.dp) {
            val movementVector: PointF = movementVector.times(PointF(-1f, 1f))

            newSpeed = length(movementVector)
            newAngle = movementVector.angle()

            if (xOffset < 0.dp) newPosX = 0f
            if (xOffset > screenSize.width - size.dp) newPosX =
                screenSize.width.value - size
        } else if (yOffset > screenSize.height - size.dp || yOffset < 0.dp) {
            val movementVector = movementVector.times(PointF(1f, -1f))

            newSpeed = length(movementVector)
            newAngle = movementVector.angle()

            if (yOffset < 0.dp) newPosY = 0f
            if (yOffset > screenSize.height - size.dp) newPosY =
                screenSize.height.value - size
        }

        return copy( // TODO: заменить на отправку новых парамтеров. copy только в редьюсере
            speed = newSpeed,
            angle = newAngle,
            position = PointF(newPosX, newPosY)
        )
    }

    suspend fun updatePlayer(
        dt: Float,
        playerData: PlayerData?,
        onMsg: suspend (GameStoreFactory.Msg) -> Unit
    ) {
        if (playerData == null) return

        val velocity = playerData.movementVector.times(dt)
        val newPosition = playerData.position.plus(velocity)
        val ylim = 300f
        if (newPosition.y > ylim) {
            onMsg.invoke(GameStoreFactory.Msg.PlayerPositionUpdated(newPosition.copy(y = ylim)))
        } else if (newPosition.y < -ylim) {
            onMsg.invoke(GameStoreFactory.Msg.PlayerPositionUpdated(newPosition.copy(y = -ylim)))
        } else {
            onMsg.invoke(GameStoreFactory.Msg.PlayerPositionUpdated(newPosition))
        }
    }
}

