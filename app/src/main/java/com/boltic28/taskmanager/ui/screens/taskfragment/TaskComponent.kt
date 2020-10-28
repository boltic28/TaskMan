package com.boltic28.taskmanager.ui.screens.taskfragment

import com.boltic28.taskmanager.businesslayer.di.BusinessModule
import com.boltic28.taskmanager.di.AppModule
import dagger.Component

@TaskFragmentScope
@Component(
    modules = [AppModule::class, BusinessModule::class, TaskFragmentModule::class]
)
interface TaskComponent {

    fun inject(model: TaskFragmentModel)

    @Component.Builder
    interface Builder{
        fun createModule(module: AppModule): Builder
        fun createModule(module: BusinessModule): Builder
        fun createModule(module: TaskFragmentModule): Builder
        fun buildComponent(): TaskComponent
    }
}