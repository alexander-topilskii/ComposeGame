package com.jazzy.mycomposegame

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun Ball(ballData: BallData) {
    val ballSize = ballData.size.dp
    Box(
        Modifier
            .offset(ballData.xOffset, ballData.yOffset)
            .size(ballSize)
            .clip(CircleShape)
            .background(ballData.color)
            .clickable { ballData.isEnabled = !ballData.isEnabled }
    )
}

@Composable
fun Box(boxData: BoxData) {
    val ballSize = boxData.size.dp
    Box(
        Modifier
            .offset(boxData.xOffset, boxData.yOffset)
            .size(ballSize)
            .background(boxData.color)
            .clickable { boxData.isEnabled = !boxData.isEnabled }
    )
}

@Composable
fun Player(player: PlayerData) {
    val ballSize = player.size.dp
    Box(
        Modifier
            .offset(player.xOffset, player.yOffset)
            .size(ballSize)
            .clip(CutCornerShape(10))
            .background(player.color)
            .clickable { player.isEnabled = !player.isEnabled }
    )
}