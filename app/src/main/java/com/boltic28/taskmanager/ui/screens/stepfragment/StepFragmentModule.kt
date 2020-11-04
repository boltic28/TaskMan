package com.boltic28.taskmanager.ui.screens.stepfragment

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.di.ViewModelKey
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.*
import com.boltic28.taskmanager.ui.screens.goalfragment.GoalFragmentModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Named

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
                override fun onActionButtonClick(item: Any) {}
                override fun onViewClick(item: Any) {}
            }
        )

    @Provides
    @IntoMap
    @ViewModelKey(StepFragmentModel::class)
    fun provideViewModel(vm: StepFragmentModel): ViewModel = vm
}