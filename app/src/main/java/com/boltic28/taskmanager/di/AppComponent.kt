package com.boltic28.taskmanager.di

import com.boltic28.taskmanager.businesslayer.di.BusinessModule
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.datalayer.room.di.DataBaseModule
import com.boltic28.taskmanager.datalayer.room.di.RepositoryModule
import com.boltic28.taskmanager.ui.screens.MainActivity
import com.boltic28.taskmanager.ui.screens.main.MainFragmentModule
import com.boltic28.taskmanager.ui.screens.creator.CreatorFragment
import com.boltic28.taskmanager.ui.screens.creator.CreatorFragmentModel
import com.boltic28.taskmanager.ui.screens.goalview.GoalFragment
import com.boltic28.taskmanager.ui.screens.goalview.GoalFragmentModule
import com.boltic28.taskmanager.ui.screens.main.MainFragment
import com.boltic28.taskmanager.ui.screens.main.MainFragmentModel
import com.boltic28.taskmanager.ui.screens.settings.SettingsFragment
import com.boltic28.taskmanager.ui.screens.settings.SettingsFragmentModel
import com.boltic28.taskmanager.ui.screens.settings.SettingsModule
import com.boltic28.taskmanager.ui.screens.sign.SignFragment
import com.boltic28.taskmanager.signtools.FireUserManager
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ContextModule::class, RepositoryModule::class, DataBaseModule::class,
        SettingsModule::class, MainFragmentModule::class, GoalFragmentModule::class,
        BusinessModule::class]
)
interface AppComponent {

    fun injectModel(model: MainFragmentModel)
    fun injectModel(model: CreatorFragmentModel)
    fun injectModel(model: SettingsFragmentModel)

    fun injectFragment(fragment: SignFragment)
    fun injectFragment(fragment: MainFragment)
    fun injectFragment(fragment: CreatorFragment)
    fun injectFragment(fragment: SettingsFragment)
    fun injectFragment(fragment: GoalFragment)

    fun injectActivity(act: MainActivity)

    fun injectManager(manager: FireUserManager)

    fun injectAdapter(adapter: ItemAdapter)

    @Component.Builder
    interface DataBuilder {
        fun createDataModule(module: ContextModule): DataBuilder
        fun createServiceModule(module: RepositoryModule): DataBuilder
        fun createDataBaseModule(module: DataBaseModule): DataBuilder
        fun createSettingModule(module: SettingsModule): DataBuilder
        fun createMainFragModule(module: MainFragmentModule): DataBuilder
        fun createBusinessModule(module: BusinessModule): DataBuilder
        fun buildComponent(): AppComponent
    }
}