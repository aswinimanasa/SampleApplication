package com.example.codingexercise.di.module

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.codingexercise.viewmodel.DataViewModelFactory
import dagger.Module
import dagger.Provides


import javax.inject.Singleton


@Module
class AppModule(private val app: Application) {


  @Provides
  @Singleton
  fun provideApplication(): Application = app



  @Provides
  @Singleton
  fun provideDataViewModelFactory(
      factory: DataViewModelFactory
  ): ViewModelProvider.Factory = factory


}
