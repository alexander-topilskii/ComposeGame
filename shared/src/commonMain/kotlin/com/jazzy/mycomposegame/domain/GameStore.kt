package com.jazzy.mycomposegame.domain

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.utils.JvmSerializable
import com.jazzy.mycomposegame.GameObject

interface GameStore : Store<GameStore.Intent, GameStore.State, Nothing> {

    sealed class Intent : JvmSerializable {
        data class ChangeText(val newText: String) : Intent()
        data class ChangeDensity(val width: Dp, val height: Dp): Intent()
    }

    data class State(
        val text: String = "Text",
        val width: Dp  = 0.dp,
        val height: Dp = 0.dp,
        val gameObjects: List<GameObject> = emptyList(),
    ) : JvmSerializable
}