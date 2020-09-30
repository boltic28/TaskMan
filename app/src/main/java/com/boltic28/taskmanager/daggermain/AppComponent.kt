package com.boltic28.taskmanager.daggermain

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.datalayer.room.di.DataBaseModule
import com.boltic28.taskmanager.datalayer.room.di.ServiceModule
import com.boltic28.taskmanager.screens.MainActivity
import com.boltic28.taskmanager.screens.creator.CreatorFragment
import com.boltic28.taskmanager.screens.creator.CreatorFragmentModel
import com.boltic28.taskmanager.screens.main.MainFragment
import com.boltic28.taskmanager.screens.settings.SettingsFragment
import com.boltic28.taskmanager.screens.settings.SettingsFragmentModel
import com.boltic28.taskmanager.screens.settings.SettingsModule
import com.boltic28.taskmanager.screens.sign.SignFragment
import com.boltic28.taskmanager.signtools.FireUserManager
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, ServiceModule::class, DataBaseModule::class, SettingsModule::class])
interface AppComponent {

    fun injectModel(model: ViewModel)
    fun injectModel(model: CreatorFragmentModel)
    fun injectModel(model: SettingsFragmentModel)

    fun injectFragment(fragment: SignFragment)
    fun injectFragment(fragment: MainFragment)
    fun injectFragment(fragment: CreatorFragment)
    fun injectFragment(fragment: SettingsFragment)

    fun injectActivity(act: MainActivity)

    fun injectManager(manager: FireUserManager)

    @Component.Builder
    interface DataBuilder {
        fun createDataModule(module: ContextModule): DataBuilder
        fun createServiceModule(module: ServiceModule): DataBuilder
        fun createDataBaseModule(module: DataBaseModule): DataBuilder
        fun createSettingModule(module: SettingsModule): DataBuilder
        fun buildComponent(): AppComponent
    }
}