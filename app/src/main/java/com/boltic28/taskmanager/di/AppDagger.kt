package com.boltic28.taskmanager.di

import android.app.Application
import com.boltic28.taskmanager.businesslayer.di.BusinessModule
import com.boltic28.taskmanager.datalayer.room.di.DataBaseModule
import com.boltic28.taskmanager.datalayer.room.di.RepositoryModule
import com.boltic28.taskmanager.ui.screens.goalfragment.DaggerGoalComponent
import com.boltic28.taskmanager.ui.screens.goalfragment.GoalComponent
import com.boltic28.taskmanager.ui.screens.goalfragment.GoalFragmentModule
import com.boltic28.taskmanager.ui.screens.mainfragment.DaggerMainComponent
import com.boltic28.taskmanager.ui.screens.mainfragment.MainComponent
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragmentModule
import com.boltic28.taskmanager.ui.screens.settings.SettingsModule
import com.boltic28.taskmanager.ui.screens.stepfragment.DaggerStepComponent
import com.boltic28.taskmanager.ui.screens.stepfragment.StepComponent
import com.boltic28.taskmanager.ui.screens.stepfragment.StepFragmentModule

class AppDagger : Application() {

    companion object {
        lateinit var component: AppComponent
        lateinit var goalComponent: GoalComponent
        lateinit var mainComponent: MainComponent
        lateinit var stepComponent: StepComponent
    }

    override fun onCreate() {
        super.onCreate()

        val contextModule = AppModule(this)
        val dataBaseModule =
            DataBaseModule(this)
        val repositoryModule =
            RepositoryModule(
                dataBaseModule.provideDataBase()
            )
        val settingsModule = SettingsModule(this)
        val businessModule = BusinessModule(
            repositoryModule.provideKeyService(),
            repositoryModule.provideStepService(),
            repositoryModule.provideTaskService(),
            repositoryModule.provideIdeaService(),
            repositoryModule.provideGoalService()
        )

        component = DaggerAppComponent
            .builder()
            .createModule(dataBaseModule)
            .createModule(contextModule)
            .createModule(settingsModule)
            .createModule(repositoryModule)
            .createModule(businessModule)
            .buildComponent()

        val goalModule = GoalFragmentModule()

        goalComponent = DaggerGoalComponent
            .builder()
            .createModule(contextModule)
            .createModule(businessModule)
            .createModule(goalModule)
            .buildComponent()

        val mainModule = MainFragmentModule()

        mainComponent = DaggerMainComponent
            .builder()
            .createModule(contextModule)
            .createModule(businessModule)
            .createModule(mainModule)
            .buildComponent()

        val stepModule = StepFragmentModule()

        stepComponent = DaggerStepComponent
            .builder()
            .createModule(contextModule)
            .createModule(businessModule)
            .createModule(stepModule)
            .buildComponent()
    }
}