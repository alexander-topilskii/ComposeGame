package com.jazzy.mycomposegame.domain.data

import androidx.compose.ui.graphics.Color
import dev.romainguy.kotlin.math.Float2

data class BallData(
    override val speed: Float,
    override val angle: Float,
    override val position: Float2,
    override val size: Float,
    override val isEnabled: Boolean = true,
    val color: Color = Color.Red,
) : GameUnit()

