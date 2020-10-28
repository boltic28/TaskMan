package com.boltic28.taskmanager.di

import com.boltic28.taskmanager.businesslayer.di.BusinessModule
import com.boltic28.taskmanager.datalayer.room.di.DataBaseModule
import com.boltic28.taskmanager.datalayer.room.di.RepositoryModule
import com.boltic28.taskmanager.ui.screens.creator.CreatorFragment
import com.boltic28.taskmanager.ui.screens.creator.CreatorFragmentModel
import com.boltic28.taskmanager.ui.screens.settings.SettingsFragment
import com.boltic28.taskmanager.ui.screens.settings.SettingsFragmentModel
import com.boltic28.taskmanager.ui.screens.settings.SettingsModule
import com.boltic28.taskmanager.ui.screens.sign.SignFragment
import com.boltic28.taskmanager.signtools.FireUserManager
import dagger.Component

@AppScope
@Component(
    modules = [
        AppModule::class, RepositoryModule::class, DataBaseModule::class,
        SettingsModule::class, BusinessModule::class]
)
interface AppComponent {

    fun injectModel(model: CreatorFragmentModel)
    fun injectModel(model: SettingsFragmentModel)

    fun injectFragment(fragment: SignFragment)
    fun injectFragment(fragment: CreatorFragment)
    fun injectFragment(fragment: SettingsFragment)
    fun injectManager(manager: FireUserManager)

    @Component.Builder
    interface DataBuilder {
        fun createModule(module: AppModule): DataBuilder
        fun createModule(module: RepositoryModule): DataBuilder
        fun createModule(module: DataBaseModule): DataBuilder
        fun createModule(module: SettingsModule): DataBuilder
        fun createModule(module: BusinessModule): DataBuilder
        fun buildComponent(): AppComponent
    }
}