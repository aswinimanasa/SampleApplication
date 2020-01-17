package com.example.codingexercise.api

import retrofit2.Response

/**
 * Common class used by API responses.
 * @param <T> the type of the response object
</T> */
@Suppress("unused") // T is used in extending classes
sealed class ApiResponse<T> {
    companion object {
        /*fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(error.message ?: "unknown error")
        }*/

        fun <T> create(error: Throwable, code: Int): ApiErrorResponse<T> {
            return ApiErrorResponse(error, error.message ?: "unknown error", code)
        }


        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse(response.code())
                } else {
                    ApiSuccessResponse(
                            body = body
                    )
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                ApiErrorResponse(Throwable(errorMsg), errorMsg ?: "unknown error", response.code())
            }
        }
    }
}

/**
 * separate class for HTTP 204 resposes so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T>(code: Int) : ApiResponse<T>()

data class ApiSuccessResponse<T>(
        val body: T
) : ApiResponse<T>() {
    constructor(body: T, linkHeader: String?) : this(
            body = body
    )

}

data class ApiErrorResponse<T>(val error: Throwable, val errorMessage: String, val code: Int) : ApiResponse<T>()
