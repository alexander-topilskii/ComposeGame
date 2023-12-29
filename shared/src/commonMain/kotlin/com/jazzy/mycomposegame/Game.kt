package com.jazzy.mycomposegame


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.unit.dp
import com.jazzy.mycomposegame.domain.ScreenSize
import com.jazzy.mycomposegame.input.KeyHandler
import dev.romainguy.kotlin.math.Float2
import kotlin.math.abs
import kotlin.random.Random

enum class GameState {
    STOPPED, RUNNING
}

class Game {
    val keyHandler = KeyHandler().apply {
        val moveSpeed = 1f
        onMoveUp = { player.movementVector = Float2(0f, -moveSpeed) }
        onMoveDown = { player.movementVector = Float2(0f, moveSpeed) }
        onMoveLeft = { player.movementVector = Float2(-moveSpeed, 0f) }
        onMoveRight = { player.movementVector = Float2(moveSpeed, 0f) }
        onSpace = { /* Логика прыжка, если необходима */ }
    }
    var prevTime = 0L

    var width by mutableStateOf(0.dp)
    var height by mutableStateOf(0.dp)

    var gameObjects = mutableStateListOf<GameObject>()
    var gameState by mutableStateOf(GameState.STOPPED)
    var gameStatus by mutableStateOf("Let's play!")
    var totalTime by mutableStateOf(0L)
    private var player = PlayerData(color = Color.Green)


    fun startGame() {
        gameObjects.clear()

        player.apply {
            position = Float2(
                width.value / 2f,
                height.value / 2f
            )
        }
        gameObjects.add(player)

        repeat(10) {
            val box = BoxData(
                ballSize = random(25, 45),
                color = Color.DarkGray
            )

            box.position =
                Float2(
                    random(0f, abs(width.value - box.size)),
                    random(0f, abs(height.value - box.size))
                )
            box.movementVector = Float2(0f, 0f)
            box.speed = 0f
            box.angle = 0f

            gameObjects.add(box)
        }


        gameState = GameState.RUNNING
        gameStatus = "Good luck!"
        totalTime = 0L
    }

    fun update(time: Long) {
        val delta = time - prevTime
        val floatDelta = (delta / 1E8).toFloat()
        prevTime = time

        if (gameState == GameState.STOPPED) return

        for (gameObject in gameObjects) {
            gameObject.update(floatDelta, ScreenSize())
        }

        totalTime += delta
    }

    fun endGame() {
        gameObjects.clear()
        gameState = GameState.STOPPED
        gameStatus = "Better luck next time!"
    }

    fun winGame() {
        gameState = GameState.STOPPED
        gameStatus = "Congratulations!"
    }

    fun onKeyPressed(keyCode: KeyEvent) {
        keyHandler.onKeyPressed(keyCode)
    }
}