package com.example.codingexercise.api


import androidx.lifecycle.LiveData
import retrofit2.http.GET




interface ApiInterface {

  @GET("facts")
  fun getDataAPI(): LiveData<ApiResponse<JsonResponse<DataModel>>>

}