package com.boltic28.taskmanager.ui.screens.keyfragment

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.ui.di.ViewModelKey
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.GoalSmallViewController
import com.boltic28.taskmanager.ui.adapter.controllers.HolderController
import com.boltic28.taskmanager.ui.adapter.controllers.IdeaSmallViewController
import com.boltic28.taskmanager.ui.adapter.controllers.TaskSmallViewController
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class KeyFragmentModule {

    @AdapterForKey
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
                override fun onActionButtonClick(item: Any) {}
                override fun onViewClick(item: Any) {}
            }
        )

    @Provides
    @IntoMap
    @ViewModelKey(KeyFragmentModel::class)
    fun provideViewModel(vm: KeyFragmentModel): ViewModel = vm
}