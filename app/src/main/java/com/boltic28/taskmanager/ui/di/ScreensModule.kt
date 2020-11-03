package com.boltic28.taskmanager.ui.di

import androidx.fragment.app.Fragment
import com.boltic28.taskmanager.ui.base.BaseEntityFragment
import com.boltic28.taskmanager.ui.base.BaseEntityFragmentModel
import com.boltic28.taskmanager.ui.screens.MainActivity
import com.boltic28.taskmanager.ui.screens.goalfragment.GoalFragmentModule
import com.boltic28.taskmanager.ui.screens.ideafragment.IdeaFragmentModule
import com.boltic28.taskmanager.ui.screens.keyfragment.KeyFragmentModule
import com.boltic28.taskmanager.ui.screens.stepfragment.StepFragmentModule
import com.boltic28.taskmanager.ui.screens.taskfragment.TaskFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ScreensModule {

//    @ContributesAndroidInjector(modules = [ActivityModule::class])
//    abstract fun activity(): MainActivity
//
//    @ContributesAndroidInjector(modules = [InteractModule::class])
//    abstract fun screensModule(): Fragment
}
