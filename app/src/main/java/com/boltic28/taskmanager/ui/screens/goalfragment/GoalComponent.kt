package com.boltic28.taskmanager.ui.screens.goalfragment

import com.boltic28.taskmanager.businesslayer.di.BusinessModule
import com.boltic28.taskmanager.di.AppModule
import dagger.Component

@GoalFragmentScope
@Component(
    modules = [GoalFragmentModule::class, AppModule::class, BusinessModule::class]
)
interface GoalComponent {

    fun inject(model: GoalFragmentModel)

    @Component.Builder
    interface Builder {
        fun createModule(module: AppModule): Builder
        fun createModule(module: BusinessModule): Builder
        fun createModule(module: GoalFragmentModule): Builder
        fun buildComponent(): GoalComponent
    }
}