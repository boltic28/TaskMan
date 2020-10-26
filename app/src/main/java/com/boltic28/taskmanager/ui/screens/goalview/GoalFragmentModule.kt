package com.boltic28.taskmanager.ui.screens.goalview

import com.boltic28.taskmanager.di.GoalFragmentScope
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.*
import dagger.Module
import dagger.Provides

@Module
class GoalFragmentModule {

    @GoalFragmentScope
    @Provides
    fun provideAdapter(): ItemAdapter =
        ItemAdapter(
            listOf(
                TaskSmallViewController(),
                StepSmallViewController(),
                IdeaSmallViewController(),
                KeySmallViewController()
            )
        )


}