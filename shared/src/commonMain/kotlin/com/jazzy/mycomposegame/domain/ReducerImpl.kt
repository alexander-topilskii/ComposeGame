package com.jazzy.mycomposegame.domain

import com.arkivanov.mvikotlin.core.store.Reducer
import com.jazzy.mycomposegame.ui.GameStoreFactory
import com.jazzy.mycomposegame.ui.GameStoreFactory.Msg.ChangeScreenParams
import com.jazzy.mycomposegame.ui.GameStoreFactory.Msg.ChangeTitleText
import com.jazzy.mycomposegame.ui.GameStoreFactory.Msg.GameUnitCreated

internal object ReducerImpl : Reducer<GameStore.State, GameStoreFactory.Msg> {
    override fun GameStore.State.reduce(msg: GameStoreFactory.Msg ): GameStore.State =
        when (msg) {
            is ChangeTitleText -> copy(text = msg.newText)
            is GameUnitCreated -> copy(gameUnits = gameUnits + msg.gameUnit)
            is ChangeScreenParams -> copy(
                screenSize = screenSize.copy(
                    width = msg.width,
                    height = msg.height
                )
            )
            is GameStoreFactory.Msg.GameUnitsUpdated -> copy(gameUnits = msg.gameUnits)
            is GameStoreFactory.Msg.PlayerUpdated -> copy(playerData = msg.player)
        }
}