package com.jazzy.mycomposegame.ui.display

import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import com.jazzy.mycomposegame.ui.GameArchElementsController

@Composable
fun String.display(controller: GameArchElementsController) {
    TextField(
        value = this,
        onValueChange = { controller.onTextChanged(it) }
    )
}
