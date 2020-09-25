package com.boltic28.taskmanager.dagger

import android.app.Application

class AppDagger: Application() {

    companion object{
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        val contextModule = ContextModule(this)
        val serviceModule = ServiceModule()

        component = DaggerAppComponent
            .builder()
            .createDataModule(contextModule)
            .createServiceModule(serviceModule)
            .buildComponent()
    }
}