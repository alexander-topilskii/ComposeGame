package com.jazzy.mycomposegame.domain;

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.jazzy.mycomposegame.database.GameDatabase
import com.jazzy.mycomposegame.database.GameEntity
import com.jazzy.mycomposegame.domain.GameStore.*
import com.jazzy.mycomposegame.ui.GameStoreFactory.Action
import com.jazzy.mycomposegame.ui.GameStoreFactory.Msg
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.withContext

 internal class ExecutorImpl(
     val database: GameDatabase<Any>,
     mainContext: CoroutineContext,
     private val ioContext: CoroutineContext
) : CoroutineExecutor<Intent, Action, State, Msg, Nothing>(mainContext) {

    override fun executeAction(action: Action) {
        when (action) {
            is Action.Init -> init()
            is Action.SaveItem -> saveItem(id = action.id)
        }
    }

    private fun init() {
        scope.launch {
             withContext(ioContext) { database.getAll() }.firstOrNull()?.first?.let {
                dispatch(Msg.Loaded(it))
            }
        }
    }

    private fun saveItem(id: String) {
        val item = state().text

        scope.launch(ioContext) {
            database.save(id, item)
        }
    }

    override fun executeIntent(intent: Intent) {
        when (intent) {
            is Intent.TextChanged -> {
                dispatch(Msg.ChangeTitleText(newText = intent.newText))
            }

            else -> {}
        }
    }
}