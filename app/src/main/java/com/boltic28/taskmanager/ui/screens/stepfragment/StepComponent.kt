package com.boltic28.taskmanager.ui.screens.stepfragment

import com.boltic28.taskmanager.businesslayer.di.BusinessModule
import com.boltic28.taskmanager.di.AppModule
import dagger.Component

@StepFragmentScope
@Component(
    modules = [BusinessModule::class, AppModule::class, StepFragmentModule::class]
)
interface StepComponent {

    fun inject(model: StepFragmentModel)

    @Component.Builder
    interface Builder{
        fun createModule(module: AppModule): Builder
        fun createModule(module: BusinessModule): Builder
        fun createModule(module: StepFragmentModule): Builder
        fun buildComponent(): StepComponent
    }
}