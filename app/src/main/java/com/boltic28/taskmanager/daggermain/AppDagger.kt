package com.boltic28.taskmanager.daggermain

import android.app.Application
import com.boltic28.taskmanager.datalayer.room.dagger.DataBaseModule
import com.boltic28.taskmanager.datalayer.room.dagger.ServiceModule

class AppDagger: Application() {

    companion object{
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        val contextModule = ContextModule(this)
        val dataBaseModule =
            DataBaseModule(this)
        val serviceModule =
            ServiceModule(
                dataBaseModule.provideDataBase()
            )

        component = DaggerAppComponent
            .builder()
            .createDataBaseModule(dataBaseModule)
            .createDataModule(contextModule)
            .createServiceModule(serviceModule)
            .buildComponent()
    }
}