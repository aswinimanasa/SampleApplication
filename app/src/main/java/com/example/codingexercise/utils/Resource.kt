package com.example.codingexercise.utils

import java.net.HttpURLConnection
import com.example.codingexercise.utils.Status.*

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<out T>(val status: Status, val data: T?, val message: String?, val error: Throwable, val code: Int) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null, Throwable(), HttpURLConnection.HTTP_OK)
        }

        fun <T> error(msg: String, error: Throwable, data: T?, code: Int): Resource<T> {
            return Resource(ERROR, data, msg, error, code)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null, Throwable(), HttpURLConnection.HTTP_OK)
        }
    }
}
