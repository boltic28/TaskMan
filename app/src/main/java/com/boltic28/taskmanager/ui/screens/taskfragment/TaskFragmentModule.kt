package com.boltic28.taskmanager.ui.screens.taskfragment

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.ui.di.ViewModelKey
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.GoalSmallViewController
import com.boltic28.taskmanager.ui.adapter.controllers.HolderController
import com.boltic28.taskmanager.ui.adapter.controllers.KeySmallViewController
import com.boltic28.taskmanager.ui.adapter.controllers.StepSmallViewController
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class TaskFragmentModule {

    @AdapterForTask
    @Provides
    fun provideAdapter(): ItemAdapter =
        ItemAdapter(
            listOf(
                GoalSmallViewController(),
                StepSmallViewController(),
                KeySmallViewController()
            ),
            object : HolderController.OnActionClickListener{
                override fun onActionButtonClick(item: Any) {}
                override fun onViewClick(item: Any) {}
            }
        )

    @Provides
    @IntoMap
    @ViewModelKey(TaskFragmentModel::class)
    fun provideViewModel(vm: TaskFragmentModel): ViewModel = vm
}