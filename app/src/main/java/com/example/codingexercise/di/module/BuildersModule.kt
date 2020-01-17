package com.example.codingexercise.di.module

import com.example.codingexercise.ui.activity.DataActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector



@Module
abstract class BuildersModule {
  @ContributesAndroidInjector
  abstract fun contributeDataActivity(): DataActivity
}