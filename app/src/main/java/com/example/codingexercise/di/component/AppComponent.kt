package com.example.codingexercise.di.component

import com.example.codingexercise.Application
import com.example.codingexercise.di.module.AppModule
import com.example.codingexercise.di.module.BuildersModule
import com.example.codingexercise.di.module.NetModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
        modules = [(AndroidInjectionModule::class), (BuildersModule::class), (AppModule::class), (NetModule::class)]
        )
internal interface AppComponent {
  abstract fun inject(app:Application)
}