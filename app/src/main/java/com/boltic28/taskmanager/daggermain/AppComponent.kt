package com.boltic28.taskmanager.daggermain

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.datalayer.room.dagger.DataBaseModule
import com.boltic28.taskmanager.datalayer.room.dagger.ServiceModule
import com.boltic28.taskmanager.datalayer.room.goal.GoalService
import com.boltic28.taskmanager.datalayer.room.idea.IdeaService
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyService
import com.boltic28.taskmanager.datalayer.room.step.StepService
import com.boltic28.taskmanager.datalayer.room.task.TaskService
import com.boltic28.taskmanager.screens.MainActivity
import com.boltic28.taskmanager.screens.main.MainFragment
import com.boltic28.taskmanager.screens.sign.SignFragment
import com.boltic28.taskmanager.signtools.FireUserManager
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, ServiceModule::class, DataBaseModule::class])
interface AppComponent {

    fun injectModel(model: ViewModel)
    fun injectFragment(fragment: SignFragment)
    fun injectFragment(fragment: MainFragment)
    fun injectActivity(act: MainActivity)
    fun injectManager(manager: FireUserManager)

    fun injectService(service: GoalService)
    fun injectService(service: StepService)
    fun injectService(service: KeyService)
    fun injectService(service: TaskService)
    fun injectService(service: IdeaService)

    @Component.Builder
    interface DataBuilder {
        fun createDataModule(module: ContextModule): DataBuilder
        fun createServiceModule(module: ServiceModule): DataBuilder
        fun createDataBaseModule(module: DataBaseModule): DataBuilder
        fun buildComponent(): AppComponent
    }
}