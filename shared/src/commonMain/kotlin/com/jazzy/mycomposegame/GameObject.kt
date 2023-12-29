package com.jazzy.mycomposegame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jazzy.mycomposegame.domain.ScreenSize
import dev.romainguy.kotlin.math.Float2
import dev.romainguy.kotlin.math.length

abstract class GameObject(
    speed: Float = 0.0F,
    angle: Float = 0.0F,
    position: Float2 = Float2(),
    var isEnabled: Boolean = true
) {
    companion object {
        val UNIT_X = Float2(1.0f, 0.0f)
    }

    var speed by mutableStateOf(speed)
    var angle by mutableStateOf(angle)
    var position by mutableStateOf(position)
    var movementVector
        get() = (UNIT_X.times(speed)).rotate(angle)
        set(value) {
            speed = length(value)
            angle = value.angle()
        }
    abstract val size: Float // Diameter

    open fun update(realDelta: Float, screenSize: ScreenSize) {
        val obj = this
        val velocity = movementVector.times(realDelta)
        obj.position = obj.position.plus(velocity)
    }
}

abstract class GameUnit() {
    abstract val speed: Float
    abstract val angle: Float
    abstract val position: Float2
    abstract val isEnabled: Boolean
    abstract val size: Float

    companion object {
        val UNIT_X = Float2(1.0f, 0.0f)
    }

    val movementVector
        get() = (UNIT_X.times(speed)).rotate(angle)
}

data class GameUnitBallData(
    override val speed: Float,
    override val angle: Float,
    override val position: Float2,
    override val size: Float,
    override val isEnabled: Boolean = true,
    val color: Color = Color.Red,
) : GameUnit()
