package com.jazzy.mycomposegame.ui.display

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jazzy.mycomposegame.domain.data.BallData
import com.jazzy.mycomposegame.domain.data.BoxData
import com.jazzy.mycomposegame.domain.data.GameUnit
import com.jazzy.mycomposegame.domain.data.RectangleData
import com.jazzy.mycomposegame.xOffset
import com.jazzy.mycomposegame.yOffset


@Composable
fun GameUnit.GameUnitDisplay() {
    when (this) {
        is BallData -> BallDisplay(this)
        is BoxData -> BoxDisplay(this)
        is RectangleData -> RectangleDisplay(this)
    }
}

@Composable
fun BoxDisplay(boxData: BoxData) {
    val ballSize = boxData.size.dp
    Box(
        Modifier
            .offset(boxData.xOffset, boxData.yOffset)
            .size(ballSize)
            .background(boxData.color)
            .clickable { }
    )
}

@Composable
fun RectangleDisplay(rectangle: RectangleData) {
    val rectangleSizeX = rectangle.size.dp
    val rectangleSizeY = rectangle.sizeY.dp
    Box(
        Modifier
            .offset(rectangle.xOffset, rectangle.yOffset)
            .size(rectangleSizeX, rectangleSizeY)
            .background(rectangle.color)
            .clickable { }
    )
}