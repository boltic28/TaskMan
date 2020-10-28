package com.boltic28.taskmanager.ui.screens.keyfragment

import com.boltic28.taskmanager.businesslayer.di.BusinessModule
import com.boltic28.taskmanager.di.AppModule
import dagger.Component

@KeyFragmentScope
@Component(
    modules = [BusinessModule::class, AppModule::class, KeyFragmentModule::class]
)
interface KeyComponent {
    fun inject(model: KeyFragmentModel)

    @Component.Builder
    interface Builder{
        fun createModule(module: AppModule): Builder
        fun createModule(module: BusinessModule): Builder
        fun createModule(module: KeyFragmentModule): Builder
        fun buildComponent(): KeyComponent
    }
}