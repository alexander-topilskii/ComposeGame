package com.jazzy.mycomposegame.ui

import com.arkivanov.mvikotlin.core.rx.Disposable
import com.arkivanov.mvikotlin.core.rx.Observer
import com.arkivanov.mvikotlin.core.rx.observer
import com.arkivanov.mvikotlin.core.view.ViewEvents
import com.jazzy.mycomposegame.database.GameEntity
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


inline fun Iterable<GameEntity>.update(
    id: String,
    func: GameEntity.Data.() -> GameEntity.Data
): List<GameEntity> =
    map { if (it.id == id) it.update(func) else it }

inline fun GameEntity.update(func: GameEntity.Data.() -> GameEntity.Data): GameEntity =
    copy(data = data.func())

/**
 * Returns a [Flow] that emits `View Events`
 */
val <Event : Any> ViewEvents<Event>.events: Flow<Event>
    get() = toFlow(ViewEvents<Event>::events)

internal inline fun <T, R> T.toFlow(
    crossinline subscribe: T.(Observer<R>) -> Disposable,
): Flow<R> =
    callbackFlow {
        val disposable =
            subscribe(
                observer(
                    onComplete = { channel.close() },
                    onNext = { channel.trySend(it) }
                )
            )

        awaitClose(disposable::dispose)
    }