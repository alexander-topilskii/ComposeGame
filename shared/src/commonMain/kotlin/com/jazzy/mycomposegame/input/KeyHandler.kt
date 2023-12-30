@file:OptIn(ExperimentalComposeUiApi::class)

package com.jazzy.mycomposegame.input

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key

object KeyHandler {

    suspend fun onKeyPressed(
        event: KeyEvent,
        onMoveUp: suspend () -> Unit = {},
        onMoveDown: suspend () -> Unit = {},
        onMoveLeft: suspend () -> Unit = {},
        onMoveRight: suspend () -> Unit = {},
        onSpace: suspend () -> Unit = {},
        onCtrl: suspend () -> Unit = {},
        onShift: suspend () -> Unit = {}
    ): Boolean {
        when (event.key) {
            Key.DirectionUp, Key.W -> onMoveUp()
            Key.DirectionDown, Key.S -> onMoveDown()
            Key.DirectionLeft, Key.A -> onMoveLeft()
            Key.DirectionRight, Key.D -> onMoveRight()
            Key.Spacebar -> onSpace()
            Key.CtrlLeft, Key.CtrlRight -> onCtrl()
            Key.ShiftLeft, Key.ShiftRight -> onShift()
        }
        return true
    }
}
