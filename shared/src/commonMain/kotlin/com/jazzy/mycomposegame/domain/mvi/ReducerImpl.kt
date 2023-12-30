package com.jazzy.mycomposegame.domain.mvi

import com.arkivanov.mvikotlin.core.store.Reducer
import com.jazzy.mycomposegame.ui.GameStoreFactory.Msg
import com.jazzy.mycomposegame.ui.GameStoreFactory.Msg.BackgroundUnitCreated
import com.jazzy.mycomposegame.ui.GameStoreFactory.Msg.ChangeScreenParams
import com.jazzy.mycomposegame.ui.GameStoreFactory.Msg.ChangeTitleText
import com.jazzy.mycomposegame.ui.GameStoreFactory.Msg.GameUnitCreated

internal object ReducerImpl : Reducer<GameStore.State, Msg> {
    override fun GameStore.State.reduce(msg: Msg ): GameStore.State =
        when (msg) {
            is ChangeTitleText -> copy(text = msg.newText)
            is GameUnitCreated -> copy(gameUnits = gameUnits + msg.gameUnit)
            is BackgroundUnitCreated -> copy(backgroundUnits = backgroundUnits + msg.backgroundUnit)
            is ChangeScreenParams -> copy(
                screenSize = screenSize.copy(
                    width = msg.width,
                    height = msg.height
                )
            )
            is Msg.GameUnitsUpdated -> copy(gameUnits = msg.gameUnits)
            is Msg.PlayerUpdated -> copy(playerData = msg.player)
        }
}