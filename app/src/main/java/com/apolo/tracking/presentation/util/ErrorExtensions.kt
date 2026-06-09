package com.apolo.tracking.presentation.util

import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.toFriendlyMessage(): String = when (this) {
    is UnknownHostException  -> "Sin conexión a internet"
    is SocketTimeoutException -> "Tiempo de espera agotado"
    is IOException            -> "Error de red. Verifica tu conexión"
    else                      -> message ?: "Error inesperado"
}
