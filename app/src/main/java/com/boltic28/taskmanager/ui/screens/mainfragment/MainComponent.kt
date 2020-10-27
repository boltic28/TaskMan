package com.boltic28.taskmanager.ui.screens.mainfragment

import com.boltic28.taskmanager.businesslayer.di.BusinessModule
import com.boltic28.taskmanager.di.ContextModule
import com.boltic28.taskmanager.di.MainFragmentScope
import dagger.Component

@MainFragmentScope
@Component(
    modules = [ContextModule::class, BusinessModule::class, MainFragmentModule::class]
)
interface MainComponent {

    fun inject(model: MainFragmentModel)

    @Component.Builder
    interface Builder {
        fun createModule(module: ContextModule): Builder
        fun createModule(module: BusinessModule): Builder
        fun createModule(module: MainFragmentModule): Builder
        fun buildComponent(): MainComponent
    }
}