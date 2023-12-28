package com.jazzy.mycomposegame.domain

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.jazzy.mycomposegame.BoxData
import com.jazzy.mycomposegame.database.GameDatabase
import com.jazzy.mycomposegame.domain.GameStore.*
import com.jazzy.mycomposegame.random
import com.jazzy.mycomposegame.ui.GameStoreFactory.Action
import com.jazzy.mycomposegame.ui.GameStoreFactory.Msg
import dev.romainguy.kotlin.math.Float2
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.withContext

internal class ExecutorImpl(
    val database: GameDatabase<Any>,
    mainContext: CoroutineContext,
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
                database.getAll()// TODO : DELETE ME

                val screenWidth = state().width.value
                val screenHeight = state().height.value

                repeat(10) {
                    val box = BoxData(
                        ballSize = random(25, 45),
                        color = Color.DarkGray
                    ).apply {
                        position = Float2(random(0f, screenWidth - size), random(0f, screenHeight - size))
                        movementVector = Float2(0f, 0f)
                        speed = 0f
                        angle = 0f
                    }

                    dispatch(Msg.GameObjectCreated(box))
                }
            }
        }
    }

    override fun executeIntent(intent: Intent) {
        when (intent) {
            is Intent.ChangeText -> {
                dispatch(Msg.ChangeTitleText(newText = intent.newText))
            }

            is Intent.ChangeDensity -> {
                dispatch(Msg.ChangeScreenParams(width = intent.width, height = intent.height))
            }
        }
    }
}