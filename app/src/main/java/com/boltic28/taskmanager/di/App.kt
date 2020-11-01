package com.boltic28.taskmanager.di

import android.app.Application
import com.boltic28.taskmanager.businesslayer.di.BusinessModule
import com.boltic28.taskmanager.datalayer.room.di.DataBaseModule
import com.boltic28.taskmanager.datalayer.room.di.RepositoryModule
import com.boltic28.taskmanager.ui.base.BaseActivity
import com.boltic28.taskmanager.ui.screens.ActivityModule
import com.boltic28.taskmanager.ui.screens.goalfragment.DaggerGoalComponent
import com.boltic28.taskmanager.ui.screens.goalfragment.GoalComponent
import com.boltic28.taskmanager.ui.screens.goalfragment.GoalFragmentModule
import com.boltic28.taskmanager.ui.screens.ideafragment.DaggerIdeaComponent
import com.boltic28.taskmanager.ui.screens.ideafragment.IdeaComponent
import com.boltic28.taskmanager.ui.screens.ideafragment.IdeaFragmentModule
import com.boltic28.taskmanager.ui.screens.keyfragment.DaggerKeyComponent
import com.boltic28.taskmanager.ui.screens.keyfragment.KeyComponent
import com.boltic28.taskmanager.ui.screens.keyfragment.KeyFragmentModule
import com.boltic28.taskmanager.ui.screens.mainfragment.DaggerMainComponent
import com.boltic28.taskmanager.ui.screens.mainfragment.MainComponent
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragmentModule
import com.boltic28.taskmanager.ui.screens.settings.SettingsModule
import com.boltic28.taskmanager.ui.screens.stepfragment.DaggerStepComponent
import com.boltic28.taskmanager.ui.screens.stepfragment.StepComponent
import com.boltic28.taskmanager.ui.screens.stepfragment.StepFragmentModule
import com.boltic28.taskmanager.ui.screens.taskfragment.DaggerTaskComponent
import com.boltic28.taskmanager.ui.screens.taskfragment.TaskComponent
import com.boltic28.taskmanager.ui.screens.taskfragment.TaskFragmentModule

class App : Application() {

    private lateinit var applicationComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
        applicationComponent.activityComponent.inject(this)
    }

    fun tryInjectActivity(activity: BaseActivity<*>): Boolean {
        return applicationComponent.getActivityComponent(ActivityModule(activity))
            .activityInjector.maybeInject(activity)
    }







//    companion object {
//        lateinit var component: AppComponent
//        lateinit var goalComponent: GoalComponent
//        lateinit var mainComponent: MainComponent
//        lateinit var stepComponent: StepComponent
//        lateinit var keyComponent: KeyComponent
//        lateinit var ideaComponent: IdeaComponent
//        lateinit var taskComponent: TaskComponent
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//
//        val contextModule = AppModule(this)
//        val dataBaseModule =
//            DataBaseModule(this)
//        val repositoryModule =
//            RepositoryModule(
//                dataBaseModule.provideDataBase()
//            )
//        val settingsModule = SettingsModule(this)
//        val businessModule = BusinessModule(
//            repositoryModule.provideKeyService(),
//            repositoryModule.provideStepService(),
//            repositoryModule.provideTaskService(),
//            repositoryModule.provideIdeaService(),
//            repositoryModule.provideGoalService()
//        )
//
//        component = DaggerAppComponent
//            .builder()
//            .createModule(dataBaseModule)
//            .createModule(contextModule)
//            .createModule(settingsModule)
//            .createModule(repositoryModule)
//            .createModule(businessModule)
//            .buildComponent()
//
//        val goalModule = GoalFragmentModule()
//        goalComponent = DaggerGoalComponent
//            .builder()
//            .createModule(contextModule)
//            .createModule(businessModule)
//            .createModule(goalModule)
//            .buildComponent()
//
//        val mainModule = MainFragmentModule()
//        mainComponent = DaggerMainComponent
//            .builder()
//            .createModule(contextModule)
//            .createModule(businessModule)
//            .createModule(mainModule)
//            .buildComponent()
//
//        val stepModule = StepFragmentModule()
//        stepComponent = DaggerStepComponent
//            .builder()
//            .createModule(contextModule)
//            .createModule(businessModule)
//            .createModule(stepModule)
//            .buildComponent()
//
//        val keyModule = KeyFragmentModule()
//        keyComponent = DaggerKeyComponent
//            .builder()
//            .createModule(contextModule)
//            .createModule(businessModule)
//            .createModule(keyModule)
//            .buildComponent()
//
//        val ideaModule = IdeaFragmentModule()
//        ideaComponent = DaggerIdeaComponent
//            .builder()
//            .createModule(contextModule)
//            .createModule(businessModule)
//            .createModule(ideaModule)
//            .buildComponent()
//
//        val taskModule = TaskFragmentModule()
//        taskComponent = DaggerTaskComponent
//            .builder()
//            .createModule(contextModule)
//            .createModule(businessModule)
//            .createModule(taskModule)
//            .buildComponent()
//    }
}