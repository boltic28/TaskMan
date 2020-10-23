package com.boltic28.taskmanager.ui.screens.goalview

import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.*
import dagger.Module
import dagger.Provides

@Module
class GoalFragmentModule {

    @Provides
    fun provideAdapter(): ItemAdapter =
        ItemAdapter(
            listOf(
                TaskViewController(),
                StepViewController(),
                IdeaViewController(),
                KeyViewController()
            )
        )
}