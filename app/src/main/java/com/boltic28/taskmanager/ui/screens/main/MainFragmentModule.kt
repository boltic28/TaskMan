package com.boltic28.taskmanager.ui.screens.main

import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.*
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

@Module
class MainFragmentModule {



    @Provides
    fun provideAdapter(): ItemAdapter =
        ItemAdapter(
            listOf(
                GoalViewController(),
                TaskViewController(),
                StepViewController(),
                IdeaViewController(),
                KeyViewController()
            )
        )
}