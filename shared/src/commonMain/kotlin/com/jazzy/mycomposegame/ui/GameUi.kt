package com.jazzy.mycomposegame.ui

import com.arkivanov.mvikotlin.core.view.MviView

interface GameUi : MviView<GameUi.ModelUi, GameUi.UiEvent> {

    data class ModelUi(
        val changeableTextUi: ChangeableTextUi,
        val item: Item
    ) {
        // No-arg constructor for Swift.
        constructor() : this(
            changeableTextUi = ChangeableTextUi(""),
            item = Item("id", "text"),
        )

        data class ChangeableTextUi(
            val text: String
        )

        data class Item(
            val id: String,
            val text: String,
        )
    }

    sealed class UiEvent {
        data class ItemClicked(val id: String) : UiEvent()
    }
}
