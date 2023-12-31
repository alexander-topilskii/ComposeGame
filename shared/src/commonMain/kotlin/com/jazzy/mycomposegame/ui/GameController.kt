package com.jazzy.mycomposegame.ui

import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.unit.Dp
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.jazzy.mycomposegame.database.GameDatabase
import com.jazzy.mycomposegame.domain.mvi.GameStore

class GameController(
    storeFactory: StoreFactory,
    database: GameDatabase<Any>,
    screenSize: Pair<Dp, Dp>,
    dispatchers: GameDispatchers
) : BaseController(storeFactory, database, screenSize, dispatchers) {

    fun onTimerUpdated(time: Long) {
        gameStore.accept(GameStore.Intent.TimerUpdate(time))
    }

    fun onTextChanged(newText: String) {
        diffAccepted(GameStore.Intent.ChangeText(newText))
    }

    fun onDensityChanged(width: Dp, height: Dp) {
        diffAccepted(GameStore.Intent.ChangeDensity(width, height))
    }

    fun onKeyEvent(keyEvent: KeyEvent) {
        diffAccepted(GameStore.Intent.KeyPressed(keyEvent))
    }
}
