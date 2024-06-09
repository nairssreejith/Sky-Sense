package com.sreejithsnair.skysense.repository

sealed class NetworkResponse<out T> {
    data class Success<T>(val data: T) : NetworkResponse<T>()
    data class Error(val errorMessage: String) : NetworkResponse<Nothing>()

    object Loading : NetworkResponse<Nothing>()

}