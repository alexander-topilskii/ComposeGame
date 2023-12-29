package com.jazzy.mycomposegame.domain.data

import com.jazzy.mycomposegame.rotate
import dev.romainguy.kotlin.math.Float2

abstract class GameUnit {
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