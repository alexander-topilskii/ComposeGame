package com.jazzy.mycomposegame.domain.mvi

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.jazzy.mycomposegame.database.GameDatabase
import com.jazzy.mycomposegame.domain.mvi.GameStore.Intent
import com.jazzy.mycomposegame.domain.mvi.GameStore.State
import com.jazzy.mycomposegame.domain.GameUnitUpdater
import com.jazzy.mycomposegame.domain.Initialisator
import com.jazzy.mycomposegame.domain.PlayerMovementController
import com.jazzy.mycomposegame.domain.TimeToDtUpdater
import com.jazzy.mycomposegame.domain.data.BallData
import com.jazzy.mycomposegame.domain.data.PlayerData
import com.jazzy.mycomposegame.input.KeyHandler
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
            is Action.Init -> Initialisator.init(action.width, action.width, ::dispatchOnMain)
            is Action.TimerUpdated -> {
                GameUnitUpdater.updatePlayer(
                    action.dt,
                    state().playerData,
                    state().screenSize,
                    ::dispatchOnMain
                )

                GameUnitUpdater.getUpdatedUnits(
                    action.dt,
                    state().gameUnits,
                    state().screenSize,
                    ::dispatchOnMain
                )
            }

            is Action.PlayerMovementRight -> PlayerMovementController
                .movePlayerToRight(state().playerData, ::dispatchOnMain)

            is Action.PlayerMovementLeft -> PlayerMovementController
                .movePlayerToLeft(state().playerData, ::dispatchOnMain)
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

            is Intent.TimerUpdate -> TimeToDtUpdater.update(intent.time, ::forwardOnMain)

            is Intent.KeyPressed -> KeyHandler.onKeyPressed(
                intent.keyEvent,
                onMoveRight = { forwardOnMain(Action.PlayerMovementRight) },
                onMoveLeft = { forwardOnMain(Action.PlayerMovementLeft) }
            )
        }
    }
}
