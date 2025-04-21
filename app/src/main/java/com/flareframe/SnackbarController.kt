package com.flareframe

import android.os.Message
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

data class SnackbarEvent(
    val message: String,
    val action: SnackbarAction? = null
)
data class SnackbarAction(
    val name:String,
    val action:()-> Unit
)
object SnackbarController {

    private val _events = Channel<SnackbarEvent>()  // this is a stream between here and where they are called, this has subscribers
    val events = _events.receiveAsFlow()

    suspend fun sendEvent(event: SnackbarEvent){
        _events.send(event)
    }
}