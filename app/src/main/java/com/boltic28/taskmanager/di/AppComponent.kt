package com.boltic28.taskmanager.di

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.datalayer.di.RepositoryModule
import com.boltic28.taskmanager.ui.di.*
import com.boltic28.taskmanager.ui.screens.goalfragment.GoalFragmentModule
import com.boltic28.taskmanager.ui.screens.ideafragment.IdeaFragmentModule
import com.boltic28.taskmanager.ui.screens.keyfragment.KeyFragmentModule
import com.boltic28.taskmanager.ui.screens.settings.SettingsFragmentModule
import com.boltic28.taskmanager.ui.screens.stepfragment.StepFragmentModule
import com.boltic28.taskmanager.ui.screens.taskfragment.TaskFragmentModule
import dagger.Component
import dagger.Subcomponent
import dagger.android.AndroidInjectionModule

@AppScope
@Component(
    modules = [AndroidInjectionModule::class, AppModule::class]
)
interface AppComponent {

    val activityComponent: ActivityComponent

    fun inject(model: ViewModel)

    fun getActivityComponent(
        activityModule: ActivityModule,
        settingsModule: SettingsFragmentModule,
        interactModule: InteractModule,
        repositoryModule: RepositoryModule
    ): LocalActivityComponent

    @Subcomponent(
        modules = [
            ScreensModule::class, AppModule::class,
            ActivityModule::class, InteractModule::class,
            SettingsFragmentModule::class, RepositoryModule::class
        ]
    )
    interface LocalActivityComponent : ActivityComponent

    @Component.Builder
    interface Builder {
        fun addModule(module: AppModule): Builder
        fun createComponent(): AppComponent
    }
}