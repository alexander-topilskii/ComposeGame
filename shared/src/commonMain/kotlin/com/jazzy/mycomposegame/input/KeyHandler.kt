@file:OptIn(ExperimentalComposeUiApi::class)

package com.jazzy.mycomposegame.input

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key

class KeyHandler {
    var onMoveUp: () -> Unit = {}
    var onMoveDown: () -> Unit = {}
    var onMoveLeft: () -> Unit = {}
    var onMoveRight: () -> Unit = {}
    var onSpace: () -> Unit = {}
    var onCtrl: () -> Unit = {}
    var onShift: () -> Unit = {}

    fun onKeyPressed(event: KeyEvent): Boolean {
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
