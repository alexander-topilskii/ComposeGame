package com.jazzy.mycomposegame.domain

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.jazzy.mycomposegame.ui.GameStoreFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal abstract class BasicExecutorImpl<in Intent : Any, Action : Any, State : Any, Message : Any, Label : Any>(
    private val mainContext: CoroutineContext,
    private val ioContext: CoroutineContext
) : CoroutineExecutor<Intent, Action, State, GameStoreFactory.Msg, Nothing>(mainContext) {

    override fun executeIntent(intent: Intent) {
        scope.launch(ioContext) {
            executeIntentOnIo(intent)
        }
    }

    abstract suspend fun executeIntentOnIo(intent: Intent)

    override fun executeAction(action: Action) {
        scope.launch(ioContext) {
            executeActionOnIo(action)
        }
    }

    abstract suspend fun executeActionOnIo(action: Action)

    suspend fun dispatchOnMain(msg: GameStoreFactory.Msg) {
        withContext(mainContext) {
            dispatch(msg)
        }
    }
}