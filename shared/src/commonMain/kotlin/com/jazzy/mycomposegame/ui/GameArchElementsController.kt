package com.jazzy.mycomposegame.ui

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.jazzy.mycomposegame.database.GameDatabase
import com.jazzy.mycomposegame.domain.GameStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GameArchElementsController(
    storeFactory: StoreFactory,
    database: GameDatabase<Any>,
  val  dispatchers: GameDispatchers,
) {

    private val gameStore = GameStoreFactory(
        storeFactory = storeFactory,
        database = database,
        mainContext = dispatchers.main,
        ioContext = dispatchers.io,
    ).create()

    internal val state: Flow<GameUi.ModelUi> = gameStore
        .states
        .map { GameUi.ModelUi(
            GameUi.ModelUi.ChangeableTextUi(it.text),
            GameUi.ModelUi.Item("ui id", "text")
        ) }

    fun onButtonClicked() {
         gameStore.accept(GameStore.Intent.AddToState("time"))
    }

    fun onTextChanged(newText: String) {
        gameStore.accept(GameStore.Intent.TextChanged(newText))
    }
}


