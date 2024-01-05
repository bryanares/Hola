package com.brian.hola.data.utils

import java.lang.Exception

sealed class Rezults<T> {

    data class Success<T>(val data: T) : Rezults<T>()

    data class Error<T>(val message: String? = null, val exception: Exception? = null) :
        Rezults<T>()

}