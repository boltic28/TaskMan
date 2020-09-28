package com.boltic28.taskmanager.dagger

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.screens.MainActivity
import com.boltic28.taskmanager.screens.main.MainFragment
import com.boltic28.taskmanager.screens.sign.SignFragment
import com.boltic28.taskmanager.signtools.FireUserManager
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, ServiceModule::class])
interface AppComponent {

    fun injectModel(model: ViewModel)
    fun injectFragment(fragment: SignFragment)
    fun injectFragment(fragment: MainFragment)
    fun injectActivity(act: MainActivity)
    fun injectManager(manager: FireUserManager)

    @Component.Builder
    interface DataBuilder {
        fun createDataModule(module: ContextModule): DataBuilder
        fun createServiceModule(module: ServiceModule): DataBuilder
        fun buildComponent(): AppComponent
    }
}