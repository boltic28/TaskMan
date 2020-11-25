package com.boltic28.taskmanager.di

import android.app.Application
import com.boltic28.taskmanager.businesslayer.service.ServiceModule
import com.boltic28.taskmanager.datalayer.di.DataBaseModule
import com.boltic28.taskmanager.datalayer.di.RepositoryModule
import com.boltic28.taskmanager.ui.base.BaseActivity
import com.boltic28.taskmanager.ui.di.ActivityModule
import com.boltic28.taskmanager.ui.di.InteractModule
import com.boltic28.taskmanager.ui.screens.settings.SettingsFragmentModule

class App : Application() {

    lateinit var applicationComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        val appModule = AppModule(this)
        val dbModule = DataBaseModule(this)
        val repoModule = RepositoryModule(dbModule.provideDataBase(), appModule.provideFBDataBase())
        val serviceModule = ServiceModule(repoModule.provideTaskRepo())
        applicationComponent = DaggerAppComponent
            .builder()
            .addModule(appModule)
            .addModule(serviceModule)
            .createComponent()
        applicationComponent.activityComponent.inject(this)
    }

    fun tryInjectActivity(activity: BaseActivity<*>): Boolean {

        val appModule = AppModule(this)
        val activityModule = ActivityModule(activity)
        val dbModule = DataBaseModule(this)
        val repoModule = RepositoryModule(dbModule.provideDataBase(), appModule.provideFBDataBase())

        return applicationComponent.getActivityComponent(
            activityModule,
            SettingsFragmentModule(this),
            InteractModule(
                repoModule.provideKeyRepo(),
                repoModule.provideStepRepo(),
                repoModule.provideTaskRepo(),
                repoModule.provideIdeaRepo(),
                repoModule.provideGoalRepo(),
                repoModule.provideGoalRemoteRepo(),
                repoModule.provideStepRemoteRepo(),
                repoModule.provideTaskRemoteRepo(),
                repoModule.provideIdeaRemoteRepo(),
                repoModule.provideKeyRemoteRepo()
            ),
            repoModule
        )
            .activityInjector.maybeInject(activity)
    }
}