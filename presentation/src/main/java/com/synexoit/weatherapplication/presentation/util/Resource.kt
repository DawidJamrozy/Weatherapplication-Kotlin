package com.synexoit.weatherapplication.presentation.util

/**
 * Created by dawidjamrozy on 08.05.2018.
 */
class Resource<out T>(val status: Status, val message: String?, val data: T?) {
    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.Success(), null, data)
        }

        fun <T> error(message: String): Resource<T> {
            return Resource(Status.Error(), message, null)
        }

        fun <T> loading() : Resource<T> {
            return Resource(Status.Loading(), null, null)
        }
    }
}

sealed class Status {
    class Success : Status()
    class Error : Status()
    class Loading : Status()
}