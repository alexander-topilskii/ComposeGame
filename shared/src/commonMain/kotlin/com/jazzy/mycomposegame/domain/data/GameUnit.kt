package com.jazzy.mycomposegame.domain.data

import com.jazzy.mycomposegame.rotate
import dev.romainguy.kotlin.math.Float2

abstract class GameUnit {
    abstract val speed: Float
    abstract val angle: Float
    abstract val position: PointF
    abstract val isEnabled: Boolean
    abstract val size: Float

    companion object {
        val UNIT_X = PointF(1.0f, 0.0f)
    }

    val movementVector: PointF
        get() = (UNIT_X.times(speed)).rotate(angle)
}