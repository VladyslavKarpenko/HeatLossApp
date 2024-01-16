package com.chi.heat.loss.app.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

fun <T> emitFlow(action: suspend () -> T) = flow { emit(action.invoke()) }


fun <T> Flow<T>.subscribe(
    scope: CoroutineScope,
    success: suspend (value: T) -> Unit
) = scope.launch { subscribe { success.invoke(it) } }

fun <T> Flow<T>.subscribe(
    scope: CoroutineScope,
    success: suspend (value: T) -> Unit,
    error: suspend (Throwable) -> Unit = { },
    complete: () -> Unit = { }
) = scope.launch {
    subscribe(
        success = { success.invoke(it) },
        error = { error.invoke(it) }
    )
}.apply { invokeOnCompletion { complete.invoke() } }

suspend fun <T> Flow<T>.subscribe(
    success: suspend (value: T) -> Unit,
    error: suspend (Throwable) -> Unit = { }
) {
    runCatching {
        collect { success.invoke(it) }
    }.getOrElse {
        error.invoke(it)
        it.printStackTrace()
    }
}

suspend fun <T> Flow<T>.subscribe(success: suspend (value: T) -> Unit) {
    collect { success.invoke(it) }
}
