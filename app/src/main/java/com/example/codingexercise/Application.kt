package com.example.codingexercise

import android.app.Activity
import android.app.Application
import com.example.codingexercise.di.module.AppModule
import com.example.codingexercise.di.module.NetModule
import com.example.codingexercise.utils.Constants
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject
import com.example.codingexercise.di.component.DaggerAppComponent

class Application : Application(), HasActivityInjector {
  @Inject
  lateinit var activityInjector: DispatchingAndroidInjector<Activity>

  override fun onCreate() {
    super.onCreate()
    @Suppress("DEPRECATION")
    DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .netModule(NetModule(Constants.BASE_URL))
        .build()
        .inject(this)



    if (LeakCanary.isInAnalyzerProcess(this)) {
      return
    }
    LeakCanary.install(this)


  }

  override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}