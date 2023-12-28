package com.jazzy.mycomposegame.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.jazzy.mycomposegame.BallData
import com.jazzy.mycomposegame.BoxData
import com.jazzy.mycomposegame.GameObject
import com.jazzy.mycomposegame.GameState
import com.jazzy.mycomposegame.database.GameDatabase
import com.jazzy.mycomposegame.domain.GameStore.*
import com.jazzy.mycomposegame.getCurrentThread
import com.jazzy.mycomposegame.random
import com.jazzy.mycomposegame.ui.GameStoreFactory.Action
import com.jazzy.mycomposegame.ui.GameStoreFactory.Msg
import dev.romainguy.kotlin.math.Float2
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.withContext
import kotlin.random.Random

internal class ExecutorImpl(
    val database: GameDatabase<Any>,
    private val mainContext: CoroutineContext,
    private val ioContext: CoroutineContext
) : CoroutineExecutor<Intent, Action, State, Msg, Nothing>(mainContext) {

    override fun executeAction(action: Action) {
        when (action) {
            is Action.Init -> init(action.width, action.height)
        }
    }

    private fun init(width: Dp, height: Dp) {
        scope.launch {
            dispatch(Msg.ChangeScreenParams(width = width, height = height))

            withContext(ioContext) {
                val screenWidth = state().screenSize.width.value
                val screenHeight = state().screenSize.height.value

                repeat(10) {
                    val box = BoxData(
                        ballSize = random(25, 45),
                        color = Color.DarkGray
                    ).apply {
                        position =
                            Float2(random(0f, screenWidth - size), random(0f, screenHeight - size))
                        movementVector = Float2(0f, 0f)
                        speed = 0f
                        angle = 0f
                    }
                    withContext(mainContext) {
                        dispatch(Msg.GameObjectCreated(box))
                    }
                }

                repeat(1000) {
                    val ball = BallData(
                        ballSize = random(25, 45),
                        color = Color(
                            red = Random.nextFloat(),
                            green = Random.nextFloat(),
                            blue = Random.nextFloat()
                        )
                    )

                    ball.position = Float2()
                    ball.movementVector = Float2(1f, 0f)
                    ball.speed = random(0, 8) + 16f
                    ball.angle = random(0, 15) + 30f
                    withContext(mainContext) {
                        dispatch(Msg.GameObjectCreated(ball))
                    }
                }
            }
        }
    }

    override fun executeIntent(intent: Intent) {
        when (intent) {
            is Intent.ChangeText -> dispatch(Msg.ChangeTitleText(newText = intent.newText))
            is Intent.ChangeDensity -> dispatch(
                Msg.ChangeScreenParams(
                    width = intent.width,
                    height = intent.height
                )
            )

            is Intent.TimerUpdate -> scope.launch(ioContext) {
                Updater.update(
                    intent.dt,
                    state().gameObjects,
                    state().screenSize
                )
            }
        }
    }
}

object Updater {
    private var totalTime by mutableStateOf(0L)
    private var prevTime = 0L

    fun update(time: Long, gameObjects: List<GameObject>, screenSize: ScreenSize) {
        val delta = time - prevTime
        val floatDelta = (delta / 1E8).toFloat()
        prevTime = time

        for (gameObject in gameObjects) {
            gameObject.update(floatDelta, screenSize)
        }

        totalTime += delta
    }
}

