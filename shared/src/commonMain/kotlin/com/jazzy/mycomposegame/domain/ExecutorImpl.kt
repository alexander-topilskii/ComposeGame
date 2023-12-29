package com.jazzy.mycomposegame.domain

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.jazzy.mycomposegame.database.GameDatabase
import com.jazzy.mycomposegame.domain.GameStore.Intent
import com.jazzy.mycomposegame.domain.GameStore.State
import com.jazzy.mycomposegame.domain.data.GameUnitBallData
import com.jazzy.mycomposegame.random
import com.jazzy.mycomposegame.ui.GameStoreFactory.Action
import com.jazzy.mycomposegame.ui.GameStoreFactory.Msg
import dev.romainguy.kotlin.math.Float2
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class ExecutorImpl(
    val database: GameDatabase<Any>,
    private val mainContext: CoroutineContext,
    private val ioContext: CoroutineContext
) : BasicExecutorImpl<Intent, Action, State, Msg, Nothing>(mainContext, ioContext) {

    override suspend fun executeActionOnIo(action: Action) {
        when (action) {
            is Action.Init -> init(action.width, action.height)
            is Action.TimerUpdated -> {
                val newGameUnits =
                    Updater.getUpdatedUnits(action.dt, state().gameUnits, state().screenSize)
                dispatchOnMain(Msg.GameUnitsUpdated(newGameUnits))
            }
        }
    }

    private suspend fun init(width: Dp, height: Dp) {
        dispatchOnMain(Msg.ChangeScreenParams(width = width, height = height))

        repeat(1000) {
            val ball = GameUnitBallData(
                size = random(25, 45),
                color = Color.Blue,
                position = Float2(random(0f, 1000f), random(0f, 1000f)),
                speed = random(0, 8) + 16f,
                angle = random(0, 15) + 30f
            )

            dispatchOnMain(Msg.GameUnitCreated(ball))
        }
    }

    override suspend fun executeIntentOnIo(intent: Intent) {
        when (intent) {
            is Intent.ChangeText -> dispatchOnMain(Msg.ChangeTitleText(newText = intent.newText))
            is Intent.ChangeDensity -> dispatchOnMain(
                Msg.ChangeScreenParams(
                    width = intent.width,
                    height = intent.height
                )
            )

            is Intent.TimerUpdate -> Updater.update(intent.time) {
                scope.launch(mainContext) { forward(Action.TimerUpdated(it)) }
            }

        }
    }
}
