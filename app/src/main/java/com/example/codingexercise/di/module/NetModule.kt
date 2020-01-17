package com.example.codingexercise.di.module

import android.util.Log
import com.example.codingexercise.api.ApiInterface
import com.example.codingexercise.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class NetModule(private val baseUrl: String) {

  @Provides
  @Singleton
  fun providesOkHttpClient(
      httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder().addInterceptor(
      httpLoggingInterceptor).build()

  @Provides
  @Singleton
  fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor(
        HttpLoggingInterceptor.Logger { message -> Log.i("","") })
    interceptor.level = HttpLoggingInterceptor.Level.BASIC
    return interceptor
  }



  @Provides
  @Singleton
  fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Builder().client(okHttpClient).baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .build()
  }

  @Provides
  @Singleton
  fun providesApiInterface(retrofit: Retrofit): ApiInterface = retrofit.create(
      ApiInterface::class.java)
}