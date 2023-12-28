package com.jazzy.mycomposegame.domain

import com.arkivanov.mvikotlin.core.store.Reducer
import com.jazzy.mycomposegame.ui.GameStoreFactory

internal object ReducerImpl : Reducer<GameStore.State, GameStoreFactory.Msg> {
     override fun GameStore.State.reduce(msg: GameStoreFactory.Msg): GameStore.State =
        when (msg) {
            is GameStoreFactory.Msg.ChangeTitleText -> copy(text = msg.newText)
            else -> this
        }
}