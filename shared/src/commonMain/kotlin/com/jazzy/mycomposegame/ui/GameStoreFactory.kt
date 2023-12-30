package com.jazzy.mycomposegame.ui

import androidx.compose.ui.unit.Dp
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.JvmSerializable
import com.jazzy.mycomposegame.domain.data.GameUnit
import com.jazzy.mycomposegame.database.GameDatabase
import com.jazzy.mycomposegame.domain.data.PlayerData
import com.jazzy.mycomposegame.domain.mvi.ExecutorImpl
import com.jazzy.mycomposegame.domain.mvi.GameStore
import com.jazzy.mycomposegame.domain.mvi.ReducerImpl
import com.jazzy.mycomposegame.domain.data.PointF
import kotlin.coroutines.CoroutineContext

class GameStoreFactory(
    private val storeFactory: StoreFactory,
    private val database: GameDatabase<Any>,
    private val mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
    private val screenSize: Pair<Dp, Dp>
) {

    fun create(): GameStore =
        object : GameStore,
            Store<GameStore.Intent, GameStore.State, Nothing> by storeFactory.create(
                name = "GameStore",
                initialState = GameStore.State(),
                bootstrapper = SimpleBootstrapper(Action.Init(screenSize.first, screenSize.second)),
                executorFactory = { ExecutorImpl(database, mainContext, ioContext) },
                reducer = ReducerImpl,
            ) {

        }

    sealed interface Action : JvmSerializable {
        data class Init(val width: Dp, val height: Dp) : Action
        data class TimerUpdated(val dt: Float) : Action
        object PlayerMovementRight : Action
        object PlayerMovementLeft : Action
    }

    sealed interface Msg : JvmSerializable {
        data class ChangeScreenParams(val width: Dp, val height: Dp) : Msg
        data class ChangeTitleText(val newText: String) : Msg
        data class GameUnitCreated(val gameUnit: GameUnit) : Msg
        data class BackgroundUnitCreated(val backgroundUnit: GameUnit) : Msg
        data class GameUnitsUpdated(val gameUnits: List<GameUnit>) : Msg
        data class PlayerPositionUpdated(val position: PointF) : Msg
        data class PlayerMovementUpdated(val speed: Float, val angle: Float) : Msg
        data class PlayerCreated(val player: PlayerData) : Msg

    }
}


