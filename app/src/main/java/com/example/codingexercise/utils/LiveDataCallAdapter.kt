package com.example.codingexercise.utils

import androidx.lifecycle.LiveData
import com.example.codingexercise.api.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A Retrofit adapter that converts the Call into a LiveData of ApiResponse.
 * @param <R>
</R> */
class LiveDataCallAdapter<R>(private val responseType: Type) :
        CallAdapter<R, LiveData<ApiResponse<R>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): LiveData<ApiResponse<R>> {
        return object : LiveData<ApiResponse<R>>() {
            private var started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            try {
                                postValue(ApiResponse.create(response))
                            } catch (e: Exception) {
                            }
                        }

                        override fun onFailure(call: Call<R>, throwable: Throwable) {
                            val code: Int = try {
                                call.execute().code()
                            } catch (e: Exception) {
                                0
                            }
                            postValue(ApiResponse.create(throwable, code))
                        }
                    })
                }
            }
        }
    }
}
