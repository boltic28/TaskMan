package com.boltic28.taskmanager.dagger

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.presentation.MainActivity
import com.boltic28.taskmanager.presentation.MainFragment
import com.boltic28.taskmanager.presentation.SignFragment
import com.boltic28.taskmanager.signin.FireUserManager
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