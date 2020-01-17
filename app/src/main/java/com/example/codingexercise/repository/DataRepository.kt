package com.example.codingexercise.repository



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.codingexercise.api.ApiInterface
import com.example.codingexercise.api.DataModel
import com.example.codingexercise.api.JsonResponse
import com.example.codingexercise.utils.Resource
import javax.inject.Inject

class DataRepository @Inject constructor(private val appExecutors: AppExecutors , private val apiInterface: ApiInterface) {

  fun dataList(): LiveData<Resource<JsonResponse<DataModel>>> {

    var dataResponse: JsonResponse<DataModel>? = null

    return object : NetworkBoundResource<JsonResponse<DataModel>>(appExecutors) {

      override fun loadFromDb():  LiveData<JsonResponse<DataModel>> {
        val mld: MutableLiveData<JsonResponse<DataModel>> = MutableLiveData()
        if (dataResponse == null) {
          dataResponse = JsonResponse()
        }
        mld.value = dataResponse
        return mld
      }
      override fun shouldFetch(data: JsonResponse<DataModel>?): Boolean {
        return true
      }

      override fun saveCallResult(item: JsonResponse<DataModel>) {
        dataResponse = item
      }

      override fun createCall() = apiInterface.getDataAPI()

    }.asLiveData()
  }
}







