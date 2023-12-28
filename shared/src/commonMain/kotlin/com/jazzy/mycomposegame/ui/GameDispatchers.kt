package com.jazzy.mycomposegame.ui

import kotlinx.coroutines.CoroutineDispatcher

interface GameDispatchers {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}