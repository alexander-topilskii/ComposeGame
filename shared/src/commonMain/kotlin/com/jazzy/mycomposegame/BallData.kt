package com.jazzy.mycomposegame

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jazzy.mycomposegame.domain.ScreenSize
import dev.romainguy.kotlin.math.Float2

class BallData(
    ballSize: Float = 20f,
    val color: Color = Color.Red,
    override var size: Float = ballSize
) : GameObject() {

    override fun update(realDelta: Float, screenSize: ScreenSize) {
        if (!isEnabled) return

        super.update(realDelta, screenSize)

        if (xOffset > screenSize.width - size.dp || xOffset < 0.dp) {
            movementVector = movementVector.times(Float2(-1f, 1f))
            if (xOffset < 0.dp) position.x = 0f
            if (xOffset > screenSize.width - size.dp) position.x = screenSize.width.value - size
        } else if (yOffset > screenSize.height - size.dp || yOffset < 0.dp) {
            movementVector = movementVector.times(Float2(1f, -1f))

            if (yOffset < 0.dp) position.y = 0f
            if (yOffset > screenSize.height - size.dp) position.y = screenSize.height.value - size
        }
    }
}
