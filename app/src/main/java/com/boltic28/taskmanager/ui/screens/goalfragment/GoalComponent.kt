package com.boltic28.taskmanager.ui.screens.goalfragment

import com.boltic28.taskmanager.businesslayer.di.BusinessModule
import com.boltic28.taskmanager.di.ContextModule
import com.boltic28.taskmanager.di.GoalFragmentScope
import dagger.Component

@GoalFragmentScope
@Component(
    modules = [GoalFragmentModule::class, ContextModule::class, BusinessModule::class]
)
interface GoalComponent {

    fun inject(model: GoalFragmentModel)

    @Component.Builder
    interface Builder {
        fun createModule(module: ContextModule): Builder
        fun createModule(module: BusinessModule): Builder
        fun createModule(module: GoalFragmentModule): Builder
        fun buildComponent(): GoalComponent
    }
}