package com.apolo.tracking.presentation.util

import com.apolo.tracking.domain.model.ApiException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.toFriendlyMessage(): String = when (this) {
    is UnknownHostException   -> "Sin conexión a internet"
    is SocketTimeoutException -> "Tiempo de espera agotado"
    is ApiException -> when (code) {
        401 -> "Usuario o contraseña incorrectos"
        403 -> "No tienes permisos para realizar esta acción"
        404 -> "Recurso no encontrado"
        500 -> "Error en el servidor. Intenta más tarde"
        else -> "Error del servidor ($code)"
    }
    is IOException -> "Error de red. Verifica tu conexión"
    else           -> message ?: "Error inesperado"
}
