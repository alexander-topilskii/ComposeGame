package com.jazzy.mycomposegame.ui

import androidx.compose.ui.unit.Dp
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.jazzy.mycomposegame.database.GameDatabase
import com.jazzy.mycomposegame.domain.mvi.GameStore
import kotlinx.coroutines.flow.Flow

abstract class BaseController(
    storeFactory: StoreFactory,
    database: GameDatabase<Any>,
    screenSize: Pair<Dp, Dp>,
    dispatchers: GameDispatchers
) {
     val gameStore = GameStoreFactory(
        storeFactory = storeFactory,
        database = database,
        mainContext = dispatchers.main,
        ioContext = dispatchers.io,
        screenSize = screenSize
    ).create()

    internal val state: Flow<GameStore.State> = this.gameStore.states

    private val intentsMap = mutableSetOf<GameStore.Intent>()

    internal fun diffAccepted(intent: GameStore.Intent) {
        if (intentsMap.contains(intent)) {
            return
        } else {
            intentsMap.add(intent)
            gameStore.accept(intent)
        }
    }
}
