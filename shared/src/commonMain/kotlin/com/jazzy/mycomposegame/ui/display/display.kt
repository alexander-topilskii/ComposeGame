package com.jazzy.mycomposegame.ui.display

import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import com.jazzy.mycomposegame.ui.GameArchElementsController
import com.jazzy.mycomposegame.ui.GameUi

@Composable
fun GameUi.ModelUi.ChangeableTextUi.display(controller: GameArchElementsController) {
    TextField(
        value = this.text,
        onValueChange = { controller.onTextChanged(it) }
    )
}
