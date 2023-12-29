package com.jazzy.mycomposegame.domain

import androidx.compose.ui.unit.dp
import com.jazzy.mycomposegame.domain.data.GameUnit
import com.jazzy.mycomposegame.angle
import com.jazzy.mycomposegame.domain.data.GameUnitBallData
import dev.romainguy.kotlin.math.Float2
import dev.romainguy.kotlin.math.length

object Updater {
    private var totalTime = 0L
    private var prevTime = 0L

    fun update(time: Long, onTic: (Float)-> Unit) {
        val delta = time - prevTime
        val floatDelta = (delta / 1E8).toFloat()
        prevTime = time

        onTic.invoke(floatDelta)

        totalTime += delta
    }

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

        return copy(
            speed = newSpeed,
            angle = newAngle,
            position = Float2(newPosX, newPosY)
        )
    }
}