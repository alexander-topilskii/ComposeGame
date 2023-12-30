package com.jazzy.mycomposegame.domain

import androidx.compose.ui.unit.dp
import com.jazzy.mycomposegame.angle
import com.jazzy.mycomposegame.domain.GameUnitUpdater.getUpdatedBallData
import com.jazzy.mycomposegame.domain.data.GameUnit
import com.jazzy.mycomposegame.domain.data.GameUnitBallData
import com.jazzy.mycomposegame.domain.data.PlayerData
import dev.romainguy.kotlin.math.Float2
import dev.romainguy.kotlin.math.length

object GameUnitUpdater {

    fun getUpdatedUnits(
        dt: Float,
        gameUnits: List<GameUnit>,
        screenSize: ScreenSize
    ): List<GameUnit> {
        return gameUnits.map { gu ->
            return@map when (gu) {
                is GameUnitBallData -> gu.getUpdatedBallData(dt, screenSize)
                else -> gu
            }
        }
    }

    suspend fun movePlayerToRight(player: PlayerData?, action: suspend (PlayerData?) -> Unit) {
        if (player == null) action.invoke(null)
        else {
            action.invoke(
                player.copy(angle = 0f, speed = player.speed + 1f)
            )
        }
    }

    suspend fun movePlayerToLeft(player: PlayerData?, action: suspend (PlayerData?) -> Unit) {
        if (player == null) action.invoke(null)
        else {
            action.invoke(
                player.copy(angle = 180f, speed = player.speed + 1f)
            )
        }
    }


    private fun GameUnitBallData.getUpdatedBallData(
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
            val movementVector = movementVector.times(Float2(-1f, 1f))

            newSpeed = length(movementVector)
            newAngle = movementVector.angle()

            if (xOffset < 0.dp) newPosX = 0f
            if (xOffset > screenSize.width - size.dp) newPosX =
                screenSize.width.value - size
        } else if (yOffset > screenSize.height - size.dp || yOffset < 0.dp) {
            val movementVector = movementVector.times(Float2(1f, -1f))

            newSpeed = length(movementVector)
            newAngle = movementVector.angle()

            if (yOffset < 0.dp) newPosY = 0f
            if (yOffset > screenSize.height - size.dp) newPosY =
                screenSize.height.value - size
        }

        return copy( // TODO: заменить на отправку новых парамтеров. copy только в редьюсере
            speed = newSpeed,
            angle = newAngle,
            position = Float2(newPosX, newPosY)
        )
    }

    suspend fun updatePlayer(
        dt: Float,
        playerData: PlayerData?,
        screenSize: ScreenSize,
        onPlayerChanged: suspend (PlayerData?) -> Unit
    ) {
        if (playerData == null) {
            onPlayerChanged.invoke(null)
            return
        }

        val velocity = playerData.movementVector.times(dt)
        val newPosition = playerData.position.plus(velocity)

        onPlayerChanged.invoke(playerData.copy(position = newPosition))
    }
}