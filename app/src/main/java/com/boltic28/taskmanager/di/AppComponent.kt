package com.boltic28.taskmanager.di

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.datalayer.di.RepositoryModule
import com.boltic28.taskmanager.ui.di.*
import com.boltic28.taskmanager.ui.screens.settings.SettingsFragmentModule
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

    @ActivityScope
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