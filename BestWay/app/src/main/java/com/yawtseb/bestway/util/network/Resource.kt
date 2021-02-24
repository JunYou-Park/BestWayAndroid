package com.airetefacruo.myapplication.util.network

data class Resource<out T>(val status: Status, val data: T?, val message: String? = null, val intMsg: Int? = null) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(status = Status.SUCCESS, data = data, message = null)
        }

        fun <T> successInt(msg: Int, data: T?): Resource<T> {
            return Resource(status = Status.SUCCESS_INT, data = data, intMsg = msg)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(status = Status.ERROR, data = data, message = msg)
        }

        fun <T> errorInt(msg: Int, data: T?): Resource<T> {
            return Resource(status = Status.ERROR_INT, data = data, intMsg = msg)
        }
        fun <T> loading(data: T?): Resource<T> {
            return Resource(status = Status.LOADING, data = data, message = null)
        }
    }
}