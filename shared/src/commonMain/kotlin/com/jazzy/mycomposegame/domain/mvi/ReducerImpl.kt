package com.jazzy.mycomposegame.domain.mvi

import com.arkivanov.mvikotlin.core.store.Reducer
import com.jazzy.mycomposegame.ui.GameStoreFactory.Msg
import com.jazzy.mycomposegame.ui.GameStoreFactory.Msg.*

internal object ReducerImpl : Reducer<GameStore.State, Msg> {
    override fun GameStore.State.reduce(msg: Msg): GameStore.State =
        when (msg) {
            is ChangeTitleText -> copy(text = msg.newText)
            is ChangeScreenParams -> copy(
                screenSize = screenSize.copy(
                    width = msg.width,
                    height = msg.height
                )
            )

            // --- Game Unit Reducer ---
            is GameUnitCreated -> copy(gameUnits = gameUnits + msg.gameUnit)
            is BackgroundUnitCreated -> copy(backgroundUnits = backgroundUnits + msg.backgroundUnit)
            is GameUnitsUpdated -> copy(gameUnits = msg.gameUnits)

            // --- Player Reducer ---
            is PlayerCreated -> copy(playerData = msg.player)
            is PlayerPositionUpdated -> copy(playerData = playerData?.copy(position = msg.position))
            is PlayerMovementUpdated -> copy(
                playerData = playerData?.copy(
                    speed = msg.speed,
                    angle = msg.angle
                )
            )

        }
}