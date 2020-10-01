package com.boltic28.taskmanager.dagger

import android.app.Application
import com.boltic28.taskmanager.datalayer.room.di.DataBaseModule
import com.boltic28.taskmanager.datalayer.room.di.ServiceModule
import com.boltic28.taskmanager.ui.screens.main.MainFragmentModule
import com.boltic28.taskmanager.ui.screens.settings.SettingsModule

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
        val adapterModule =
            MainFragmentModule()


        component = DaggerAppComponent
            .builder()
            .createDataBaseModule(dataBaseModule)
            .createDataModule(contextModule)
            .createSettingModule(settingsModule)
            .createServiceModule(serviceModule)
            .createMainFragModule(adapterModule)
            .buildComponent()
    }
}