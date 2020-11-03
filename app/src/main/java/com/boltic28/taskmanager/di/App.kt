package com.boltic28.taskmanager.di

import android.app.Application
import com.boltic28.taskmanager.datalayer.di.DataBaseModule
import com.boltic28.taskmanager.datalayer.di.RepositoryModule
import com.boltic28.taskmanager.ui.base.BaseActivity
import com.boltic28.taskmanager.ui.base.BaseFragment
import com.boltic28.taskmanager.ui.di.ActivityModule
import com.boltic28.taskmanager.ui.di.FragmentModule
import com.boltic28.taskmanager.ui.di.InteractModule
import com.boltic28.taskmanager.ui.screens.goalfragment.GoalFragmentModule
import com.boltic28.taskmanager.ui.screens.ideafragment.IdeaFragmentModule
import com.boltic28.taskmanager.ui.screens.keyfragment.KeyFragmentModule
import com.boltic28.taskmanager.ui.screens.stepfragment.StepFragmentModule
import com.boltic28.taskmanager.ui.screens.taskfragment.TaskFragmentModule

class App : Application() {

    lateinit var applicationComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerAppComponent
            .builder()
            .addModule(AppModule(this))
            .createComponent()
        applicationComponent.activityComponent.inject(this)
    }

    fun tryInjectActivity(activity: BaseActivity<*>): Boolean {

        val dbModule = DataBaseModule(this)
        val repoModule = RepositoryModule(dbModule.provideDataBase())

        return applicationComponent.getActivityComponent(
            ActivityModule(activity),
            InteractModule(
                repoModule.provideKeyRepo(),
                repoModule.provideStepRepo(),
                repoModule.provideTaskRepo(),
                repoModule.provideIdeaRepo(),
                repoModule.provideGoalRepo()
            ),
            GoalFragmentModule(),
            StepFragmentModule(),
            TaskFragmentModule(),
            IdeaFragmentModule(),
            KeyFragmentModule()
        )
            .activityInjector.maybeInject(activity)
    }

//    fun tryInjectFragment(fragment: BaseFragment<*>): Boolean {
//        val dbModule = DataBaseModule(this)
//        val repoModule = RepositoryModule(dbModule.provideDataBase())
//
//        return applicationComponent.getFragmentComponent(
//            FragmentModule(
//                fragment
//            ),
//            InteractModule(
//                repoModule.provideKeyRepo(),
//                repoModule.provideStepRepo(),
//                repoModule.provideTaskRepo(),
//                repoModule.provideIdeaRepo(),
//                repoModule.provideGoalRepo()
//            ),
//            GoalFragmentModule(),
//            StepFragmentModule(),
//            TaskFragmentModule(),
//            IdeaFragmentModule(),
//            KeyFragmentModule()
//        )
//            .fragmentInjector.maybeInject(fragment)
//    }

}