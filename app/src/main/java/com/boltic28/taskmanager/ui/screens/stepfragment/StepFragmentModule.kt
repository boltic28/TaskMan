package com.boltic28.taskmanager.ui.screens.stepfragment

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.datalayer.entities.BaseItem
import com.boltic28.taskmanager.ui.di.ViewModelKey
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.*
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class StepFragmentModule {

    @AdapterForStep
    @Provides
    fun provideAdapter(): ItemAdapter =
        ItemAdapter(
            listOf(
                TaskSmallViewController(),
                IdeaSmallViewController(),
                GoalSmallViewController()
            ),
            object : HolderController.OnActionClickListener{
                override fun isNeedToShowConnection(): Boolean = true
                override fun onActionButtonClick(item: BaseItem) {}
                override fun onViewClick(item: BaseItem) {}
            }
        )

    @Provides
    @IntoMap
    @ViewModelKey(StepFragmentModel::class)
    fun provideViewModel(vm: StepFragmentModel): ViewModel = vm
}