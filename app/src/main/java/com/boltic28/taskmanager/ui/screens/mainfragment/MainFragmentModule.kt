package com.boltic28.taskmanager.ui.screens.mainfragment

import com.boltic28.taskmanager.di.MainFragmentScope
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.*
import dagger.Module
import dagger.Provides

@Module
class MainFragmentModule {

    @MainFragmentScope
    @Provides
    fun provideAdapter(): ItemAdapter =
        ItemAdapter(
            listOf(
                GoalViewController(),
                TaskViewController(),
                StepViewController(),
                IdeaViewController(),
                KeyViewController()
            ),
            object : HolderController.OnActionClickListener{
                override fun onActionButtonClick(item: Any) {}
                override fun onViewClick(item: Any) {}
            }
        )
}