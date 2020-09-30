package com.boltic28.taskmanager.daggermain

import android.app.Application
import com.boltic28.taskmanager.datalayer.room.di.DataBaseModule
import com.boltic28.taskmanager.datalayer.room.di.ServiceModule
import com.boltic28.taskmanager.screens.settings.SettingsModule

class AppDagger : Application() {

    companion object {
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
        val settingsModule = SettingsModule(this)

        component = DaggerAppComponent
            .builder()
            .createDataBaseModule(dataBaseModule)
            .createDataModule(contextModule)
            .createSettingModule(settingsModule)
            .createServiceModule(serviceModule)
            .buildComponent()
    }
}