package com.jazzy.mycomposegame.domain

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.utils.JvmSerializable

interface GameStore : Store<GameStore.Intent, GameStore.State, Nothing> {

    sealed class Intent : JvmSerializable {
        data class Delete(val id: String) : Intent()
        data class ToggleDone(val id: String) : Intent()
        data class AddToState(val item: String) : Intent()
        data class DeleteFromState(val id: String) : Intent()
        data class UpdateInState(val id: String, val data: String) : Intent()
        data class TextChanged(val newText: String) : Intent()
    }

    data class State(
        val text: String = "Text"
    ) : JvmSerializable
}