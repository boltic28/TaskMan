package com.boltic28.taskmanager.ui.screens.mainfragment

import com.boltic28.taskmanager.ui.di.InteractModule
import com.boltic28.taskmanager.di.AppModule
import dagger.Component

@MainFragmentScope
@Component(
    modules = [AppModule::class, InteractModule::class, MainFragmentModule::class]
)
interface MainComponent {

    fun inject(model: MainFragmentModel)

    @Component.Builder
    interface Builder {
        fun createModule(module: AppModule): Builder
        fun createModule(module: InteractModule): Builder
        fun createModule(module: MainFragmentModule): Builder
        fun buildComponent(): MainComponent
    }
}