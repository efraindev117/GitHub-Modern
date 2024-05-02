package com.android.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

sealed class Resource<out T> {

    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val msg: Throwable?) : Resource<Nothing>()
    object Loading : Resource<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success $data"
            is Failure -> "Failure $msg"
            Loading -> "Loading"
        }
    }
}

fun <T, R> Resource<T>.map(transform: (T) -> R): Resource<R> {
    return when (this) {
        is Resource.Success -> Resource.Success(transform(data))
        is Resource.Failure -> Resource.Failure(msg)
        Resource.Loading -> Resource.Loading
    }
}

fun <T> Flow<Resource<T>>.doOnSuccess(action: suspend (T) -> Unit): Flow<Resource<T>> =
    transform { result ->
        if (result is Resource.Success) {
            action(result.data)
        }
        return@transform emit(result)
    }

fun <T> Flow<Resource<T>>.doOnFailure(action: suspend (Throwable?) -> Unit): Flow<Resource<T>> =
    transform { result ->
        if (result is Resource.Failure) {
            action(result.msg)
        }
        return@transform emit(result)
    }

fun <T> Flow<Resource<T>>.doOnLoading(action: suspend () -> Unit): Flow<Resource<T>> =
    transform { result ->
        if (result is Resource.Loading) {
            action()
        }
        return@transform emit(result)
    }