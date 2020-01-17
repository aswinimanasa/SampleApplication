package com.example.codingexercise.repository

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.codingexercise.api.*
import com.example.codingexercise.utils.Resource
import com.example.codingexercise.utils.Status


/**
 * A generic class that can provide a resource backed by  the network.
 *
 * @param <Type>
*/
abstract class NetworkBoundResource<Type>
@MainThread constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<Type>>()

    init {
        result.value = Resource.loading(null)
        val dbSource = this.loadFromDb()
        fetchFromNetwork(dbSource)
    }

    @MainThread
    private fun setValue(newValue: Resource<Type>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<Type>) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData ->
            setValue(Resource.loading(newData))
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response) {
                is ApiSuccessResponse -> {
                    appExecutors.diskIO().execute {
                        saveCallResult(processResponse(response))
                        appExecutors.mainThread().execute {
                            // we specially request a new live data,
                            // otherwise we will get immediately last cached value,
                            // which may not be updated with latest results received from network.
                            result.addSource(loadFromDb()) { newData ->
                                setValue(Resource.success(response.body))
                            }
                        }
                    }
                }
                is ApiEmptyResponse -> {
                    appExecutors.mainThread().execute {
                        // reload from disk whatever we had
                        result.addSource(loadFromDb()) { newData ->
                            setValue(Resource.success(newData))
                            Log.e("RESPONSE",newData.toString())
                        }

                    }
                }
                is ApiErrorResponse -> {
                    result.addSource(dbSource) { newData ->
                        setValue(Resource.error(response.errorMessage, response.error, newData, response.code))
                        Log.e("RESPONSE",newData.toString())
                    }
                    onFetchFailed()
                }
            }
        }
    }

    fun asLiveData() = result as LiveData<Resource<Type>>

    protected open fun onFetchFailed() = processOnFetchFailed()

    private fun processOnFetchFailed() {
        if (result.value is Resource<*>) {
            result.value as Resource<*>
            if (result.value?.status == Status.ERROR) {
                handleError(result.value?.error, result.value?.code)
            }
        }
    }

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<Type>): Type {
        if (response.body is JsonResponse<Any>) {
            parseResponse(response.body)
        }
        return response.body
    }

    private fun parseResponse(response: JsonResponse<Any>) {
        Log.e("Response",response.title)
    }

    private fun handleError(e: Throwable?, code: Int?) {
        try {
            if (e == null) return
            e.printStackTrace()
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
    }

    @WorkerThread
    protected abstract fun saveCallResult(item: Type)

    @MainThread
    protected abstract fun shouldFetch(data: Type?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<Type>
    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<Type>>
}
