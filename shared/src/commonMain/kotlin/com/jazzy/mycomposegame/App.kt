package com.jazzy.mycomposegame

import androidx.compose.runtime.Composable
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.jazzy.mycomposegame.ui.MainGame
import com.jazzy.mycomposegame.database.GameDatabase
import com.jazzy.mycomposegame.ui.GameDispatchers

@Composable
fun App(
    storeFactory: StoreFactory,
    database: GameDatabase<Any>,
    dispatchers: GameDispatchers,
) {
    MainGame(storeFactory, database, dispatchers)
}