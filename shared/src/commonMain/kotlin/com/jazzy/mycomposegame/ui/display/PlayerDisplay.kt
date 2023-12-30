package com.jazzy.mycomposegame.ui.display

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jazzy.mycomposegame.domain.data.PlayerData
import com.jazzy.mycomposegame.xOffset
import com.jazzy.mycomposegame.yOffset

@Composable
fun PlayerDisplay(player: PlayerData) {
    val ballSize = player.size.dp
    Box(
        Modifier
            .offset(player.xOffset, player.yOffset)
            .size(ballSize)
            .border(1.dp, Color.White)
            .clip(CutCornerShape(10))
            .background(player.color)
            .clickable {
                // TODO
            }
    )
}