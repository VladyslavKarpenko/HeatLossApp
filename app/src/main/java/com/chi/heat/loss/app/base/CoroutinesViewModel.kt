package com.chi.heat.loss.app.base

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlin.coroutines.CoroutineContext

open class CoroutinesViewModel : ViewModel(), CoroutineScope {

    private val errorHandler = CoroutineExceptionHandler { _, error ->
        error.printStackTrace()
        launch { _errorEvent.emit(error) }
    }

    override val coroutineContext: CoroutineContext =
        SupervisorJob() + Dispatchers.Main.immediate + errorHandler

    val ioScope = CoroutineScope(
        SupervisorJob() + Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            Log.e(CoroutinesViewModel::class.java.simpleName, "ioScope ${throwable.message}")
            throwable.printStackTrace()
        }
    )

    private val _errorEvent: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorEvent: SharedFlow<Throwable>
        get() = _errorEvent
}