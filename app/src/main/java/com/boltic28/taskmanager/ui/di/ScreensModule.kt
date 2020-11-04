package com.boltic28.taskmanager.ui.di

import com.boltic28.taskmanager.ui.screens.activity.MainActivity
import com.boltic28.taskmanager.ui.screens.activity.MainActivityModule
import com.boltic28.taskmanager.ui.screens.creator.CreatorFragment
import com.boltic28.taskmanager.ui.screens.creator.CreatorFragmentModule
import com.boltic28.taskmanager.ui.screens.goalfragment.GoalFragment
import com.boltic28.taskmanager.ui.screens.goalfragment.GoalFragmentModule
import com.boltic28.taskmanager.ui.screens.goalfragment.GoalFragmentScope
import com.boltic28.taskmanager.ui.screens.ideafragment.IdeaFragment
import com.boltic28.taskmanager.ui.screens.ideafragment.IdeaFragmentModule
import com.boltic28.taskmanager.ui.screens.ideafragment.IdeaFragmentScope
import com.boltic28.taskmanager.ui.screens.keyfragment.KeyFragment
import com.boltic28.taskmanager.ui.screens.keyfragment.KeyFragmentModule
import com.boltic28.taskmanager.ui.screens.keyfragment.KeyFragmentScope
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragmentModule
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragmentScope
import com.boltic28.taskmanager.ui.screens.settings.SettingsFragment
import com.boltic28.taskmanager.ui.screens.sign.SignFragment
import com.boltic28.taskmanager.ui.screens.sign.SignFragmentModule
import com.boltic28.taskmanager.ui.screens.stepfragment.StepFragment
import com.boltic28.taskmanager.ui.screens.stepfragment.StepFragmentModule
import com.boltic28.taskmanager.ui.screens.stepfragment.StepFragmentScope
import com.boltic28.taskmanager.ui.screens.taskfragment.TaskFragment
import com.boltic28.taskmanager.ui.screens.taskfragment.TaskFragmentModule
import com.boltic28.taskmanager.ui.screens.taskfragment.TaskFragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ScreensModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun activity(): MainActivity

    @ContributesAndroidInjector(modules = [CreatorFragmentModule::class])
    abstract fun creatorFragment(): CreatorFragment

    @ContributesAndroidInjector
    abstract fun settingFragment(): SettingsFragment

    @ContributesAndroidInjector(modules = [SignFragmentModule::class])
    abstract fun signFragment(): SignFragment

    @MainFragmentScope
    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    abstract fun mainFragment(): MainFragment

    @GoalFragmentScope
    @ContributesAndroidInjector(modules = [GoalFragmentModule::class])
    abstract fun goalFragment(): GoalFragment

    @StepFragmentScope
    @ContributesAndroidInjector(modules = [StepFragmentModule::class])
    abstract fun stepFragment(): StepFragment

    @TaskFragmentScope
    @ContributesAndroidInjector(modules = [TaskFragmentModule::class])
    abstract fun taskFragment(): TaskFragment

    @IdeaFragmentScope
    @ContributesAndroidInjector(modules = [IdeaFragmentModule::class])
    abstract fun ideaFragment(): IdeaFragment

    @KeyFragmentScope
    @ContributesAndroidInjector(modules = [KeyFragmentModule::class])
    abstract fun keyFragment(): KeyFragment
}
