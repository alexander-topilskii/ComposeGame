package com.jazzy.mycomposegame.ui

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.JvmSerializable
import com.jazzy.mycomposegame.database.GameDatabase
import com.jazzy.mycomposegame.domain.ExecutorImpl
import com.jazzy.mycomposegame.domain.GameStore
import com.jazzy.mycomposegame.domain.ReducerImpl
import kotlin.coroutines.CoroutineContext

internal class GameStoreFactory(
    private val storeFactory: StoreFactory,
    private val database: GameDatabase<Any>,
    private val mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
) {

    fun create(): GameStore =
        object : GameStore, Store<GameStore.Intent, GameStore.State, Nothing> by storeFactory.create(
            name = "GameStore",
            initialState = GameStore.State(),
            bootstrapper = SimpleBootstrapper(Action.Init),
            executorFactory = { ExecutorImpl(database, mainContext, ioContext) },
            reducer = ReducerImpl,
        ) {

        }

    sealed interface Action : JvmSerializable {
        object Init : Action
        data class SaveItem(val id: String) : Action
    }

    sealed interface Msg : JvmSerializable {
        data class ChangeTitleText(val newText: String): Msg
        data class Loaded(val newText: String) : Msg
    }

}


