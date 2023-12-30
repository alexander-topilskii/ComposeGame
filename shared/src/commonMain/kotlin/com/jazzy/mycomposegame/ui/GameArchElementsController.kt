package com.jazzy.mycomposegame.ui

import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.unit.Dp
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.jazzy.mycomposegame.database.GameDatabase
import com.jazzy.mycomposegame.domain.GameStore
import kotlinx.coroutines.flow.Flow

class GameArchElementsController(
    storeFactory: StoreFactory,
    database: GameDatabase<Any>,
    screenSize: Pair<Dp, Dp>,
    val dispatchers: GameDispatchers
) {

    private val gameStore = GameStoreFactory(
        storeFactory = storeFactory,
        database = database,
        mainContext = dispatchers.main,
        ioContext = dispatchers.io,
        screenSize = screenSize
    ).create()

    internal val state: Flow<GameStore.State> = gameStore.states

    fun onButtonClicked() {

    }

    fun onTimerUpdated(time: Long) {
        gameStore.accept(GameStore.Intent.TimerUpdate(time))
    }

    fun onTextChanged(newText: String) {
        gameStore.accept(GameStore.Intent.ChangeText(newText))
    }

    fun onDensityChanged(width: Dp, height: Dp) {
        gameStore.accept(GameStore.Intent.ChangeDensity(width, height))
    }

    fun onKeyEvent(keyEvent: KeyEvent) {
        gameStore.accept(GameStore.Intent.KeyPressed(keyEvent))
    }
}


