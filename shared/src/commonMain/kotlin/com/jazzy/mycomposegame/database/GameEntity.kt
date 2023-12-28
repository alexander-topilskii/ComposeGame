package com.jazzy.mycomposegame.database

import com.arkivanov.mvikotlin.core.utils.JvmSerializable


// Repository entity
data class GameEntity(
    val id: String,
    val data: Data
) : JvmSerializable {

    data class Data(
        val text: String,
        val isDone: Boolean = false
    ) : JvmSerializable
}