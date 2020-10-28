package com.boltic28.taskmanager.ui.screens.mainfragment

import com.boltic28.taskmanager.businesslayer.di.BusinessModule
import com.boltic28.taskmanager.di.AppModule
import dagger.Component

@MainFragmentScope
@Component(
    modules = [AppModule::class, BusinessModule::class, MainFragmentModule::class]
)
interface MainComponent {

    fun inject(model: MainFragmentModel)

    @Component.Builder
    interface Builder {
        fun createModule(module: AppModule): Builder
        fun createModule(module: BusinessModule): Builder
        fun createModule(module: MainFragmentModule): Builder
        fun buildComponent(): MainComponent
    }
}