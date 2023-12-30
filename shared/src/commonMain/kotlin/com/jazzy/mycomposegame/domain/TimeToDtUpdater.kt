package com.jazzy.mycomposegame.domain

import com.jazzy.mycomposegame.ui.GameStoreFactory

object TimeToDtUpdater {
    private var totalTime = 0L
    private var prevTime = 0L

    suspend fun update(time: Long, onTic: suspend (GameStoreFactory.Action) -> Unit) {
        val delta = time - prevTime
        val floatDelta = (delta / 1E8).toFloat()
        prevTime = time

        onTic.invoke(GameStoreFactory.Action.TimerUpdated(floatDelta))

        totalTime += delta
    }
}

