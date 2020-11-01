package com.boltic28.taskmanager.di

import com.boltic28.taskmanager.ui.screens.*
import dagger.Component
import dagger.Subcomponent

@AppScope
@Component(
    modules = [AppModule::class]
)
interface AppComponent {

    val activityComponent: ActivityComponent

    fun getActivityComponent(activityModule: ActivityModule): LocalActivityComponent

    @ActivityScope
    @Subcomponent(modules = [ActivityModule::class, ScreensModule::class])
    interface LocalActivityComponent : ActivityComponent


//-------------------------
//    fun injectModel(model: CreatorFragmentModel)
//    fun injectModel(model: SettingsFragmentModel)
//
//    fun injectFragment(fragment: SignFragment)
//    fun injectFragment(fragment: CreatorFragment)
//    fun injectFragment(fragment: SettingsFragment)
//    fun injectManager(manager: FireUserManager)
//
//    @Component.Builder
//    interface DataBuilder {
//        fun createModule(module: AppModule): DataBuilder
//        fun createModule(module: RepositoryModule): DataBuilder
//        fun createModule(module: DataBaseModule): DataBuilder
//        fun createModule(module: SettingsModule): DataBuilder
//        fun createModule(module: BusinessModule): DataBuilder
//        fun buildComponent(): AppComponent
//    }
}