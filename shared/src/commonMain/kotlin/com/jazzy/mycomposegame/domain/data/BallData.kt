package com.jazzy.mycomposegame.domain.data

import androidx.compose.ui.graphics.Color

data class BallData(
    override val speed: Float,
    override val angle: Float,
    override val position: PointF,
    override val size: Float,
    override val isEnabled: Boolean = true,
    val color: Color = Color.Red,
) : GameUnit()

data class BoxData(
    override val speed: Float,
    override val angle: Float,
    override val position: PointF,
    override val size: Float,
    override val isEnabled: Boolean = true,
    val color: Color = Color.Red,
) : GameUnit()

