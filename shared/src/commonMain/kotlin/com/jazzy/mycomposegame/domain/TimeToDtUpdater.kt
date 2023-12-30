package com.jazzy.mycomposegame.domain

object TimeToDtUpdater {
    private var totalTime = 0L
    private var prevTime = 0L

    fun update(time: Long, onTic: (Float) -> Unit) {
        val delta = time - prevTime
        val floatDelta = (delta / 1E8).toFloat()
        prevTime = time

        onTic.invoke(floatDelta)

        totalTime += delta
    }
}

