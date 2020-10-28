package com.boltic28.taskmanager.ui.screens.ideafragment

import com.boltic28.taskmanager.businesslayer.di.BusinessModule
import com.boltic28.taskmanager.di.AppModule
import dagger.Component

@IdeaFragmentScope
@Component(
    modules = [AppModule::class, BusinessModule::class, IdeaFragmentModule::class]
)
interface IdeaComponent {

    fun inject(model: IdeaFragmentModel)

    @Component.Builder
    interface Builder{
        fun createModule(module: AppModule): Builder
        fun createModule(module: BusinessModule): Builder
        fun createModule(module: IdeaFragmentModule): Builder
        fun buildComponent(): IdeaComponent
    }

}