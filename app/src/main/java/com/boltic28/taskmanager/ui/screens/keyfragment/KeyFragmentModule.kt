package com.boltic28.taskmanager.ui.screens.keyfragment

import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.GoalSmallViewController
import com.boltic28.taskmanager.ui.adapter.controllers.HolderController
import com.boltic28.taskmanager.ui.adapter.controllers.IdeaSmallViewController
import com.boltic28.taskmanager.ui.adapter.controllers.TaskSmallViewController
import dagger.Module
import dagger.Provides

@Module
class KeyFragmentModule {

    @KeyFragmentScope
    @Provides
    fun provideAdapter(): ItemAdapter =
        ItemAdapter(
            listOf(
                TaskSmallViewController(),
                IdeaSmallViewController(),
                GoalSmallViewController()
            ),
            object : HolderController.OnActionClickListener{
                override fun onActionButtonClick(item: Any) {}
                override fun onViewClick(item: Any) {}
            }
        )
}