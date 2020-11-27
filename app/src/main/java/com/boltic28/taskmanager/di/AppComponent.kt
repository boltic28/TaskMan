package com.boltic28.taskmanager.di

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.businesslayer.service.NotifyService
import com.boltic28.taskmanager.businesslayer.service.ServiceModule
import com.boltic28.taskmanager.businesslayer.syncmanager.SyncService
import com.boltic28.taskmanager.datalayer.di.RepositoryModule
import com.boltic28.taskmanager.ui.di.*
import com.boltic28.taskmanager.ui.screens.settings.SettingsFragmentModule
import dagger.Component
import dagger.Subcomponent
import dagger.android.AndroidInjectionModule

@AppScope
@Component(
    modules = [AndroidInjectionModule::class, AppModule::class, ServiceModule::class, InteractModule::class]
)
interface AppComponent {

    val activityComponent: ActivityComponent

    fun inject(model: ViewModel)
    fun inject(service: NotifyService)
    fun inject(service: SyncService)

    fun getActivityComponent(
        activityModule: ActivityModule,
        settingsModule: SettingsFragmentModule,
        repositoryModule: RepositoryModule
    ): LocalActivityComponent

    @ActivityScope
    @Subcomponent(
        modules = [
            ScreensModule::class, AppModule::class,
            ActivityModule::class,
            SettingsFragmentModule::class, RepositoryModule::class
        ]
    )
    interface LocalActivityComponent : ActivityComponent

    @Component.Builder
    interface Builder {
        fun addModule(module: AppModule): Builder
        fun addModule(module: ServiceModule): Builder
        fun addModule(module: InteractModule): Builder
        fun createComponent(): AppComponent
    }
}