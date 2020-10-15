package com.boltic28.taskmanager.di

import android.app.Application
import com.boltic28.taskmanager.businesslayer.di.BusinessModule
import com.boltic28.taskmanager.datalayer.room.di.DataBaseModule
import com.boltic28.taskmanager.datalayer.room.di.RepositoryModule
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
        val repositoryModule =
            RepositoryModule(
                dataBaseModule.provideDataBase()
            )
        val settingsModule = SettingsModule(this)
        val adapterModule =
            MainFragmentModule()
        val businessModule = BusinessModule(
            repositoryModule.provideKeyService(),
            repositoryModule.provideStepService(),
            repositoryModule.provideTaskService(),
            repositoryModule.provideIdeaService(),
            repositoryModule.provideGoalService()
        )

        component = DaggerAppComponent
            .builder()
            .createDataBaseModule(dataBaseModule)
            .createDataModule(contextModule)
            .createSettingModule(settingsModule)
            .createServiceModule(repositoryModule)
            .createMainFragModule(adapterModule)
            .createBusinessModule(businessModule)
            .buildComponent()
    }
}