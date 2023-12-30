package com.jazzy.mycomposegame.domain

import com.jazzy.mycomposegame.domain.data.PlayerData
import com.jazzy.mycomposegame.ui.GameStoreFactory

object PlayerMovementController {

    suspend fun movePlayerToRight(
        player: PlayerData?,
        action: suspend (GameStoreFactory.Msg) -> Unit
    ) {
        if (player == null) return
        else {
            action.invoke(
                GameStoreFactory.Msg.PlayerMovementUpdated(
                    angle = 0f,
                    speed = player.speed + 1f
                )
            )
        }
    }

    suspend fun movePlayerToLeft(
        player: PlayerData?,
        action: suspend (GameStoreFactory.Msg) -> Unit
    ) {
        if (player == null) return
        else {
            action.invoke(
                GameStoreFactory.Msg.PlayerMovementUpdated(
                    angle = 180f,
                    speed = player.speed + 1f
                )
            )
        }
    }
}