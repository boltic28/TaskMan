package com.boltic28.taskmanager.ui.screens.goalfragment

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.di.ViewModelKey
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.*
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Named

@Module
class GoalFragmentModule {

    @AdapterForGoal
    @Provides
    fun provideAdapter(): ItemAdapter =
        ItemAdapter(
            listOf(
                TaskSmallViewController(),
                StepSmallViewController(),
                IdeaSmallViewController(),
                KeySmallViewController()
            ),
            object : HolderController.OnActionClickListener{
                override fun onActionButtonClick(item: Any) {}
                override fun onViewClick(item: Any) {}
            }
        )

    @Provides
    @IntoMap
    @ViewModelKey(GoalFragmentModel::class)
    fun provideViewModel(vm: GoalFragmentModel): ViewModel = vm
}