package com.jazzy.mycomposegame.ui.display

import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import com.jazzy.mycomposegame.ui.GameController

@Composable
fun String.display(controller: GameController) {
    TextField(
        value = this,
        onValueChange = { controller.onTextChanged(it) }
    )
}
