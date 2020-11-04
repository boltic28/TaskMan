package com.boltic28.taskmanager.ui.screens.keyfragment

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.di.ViewModelKey
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.GoalSmallViewController
import com.boltic28.taskmanager.ui.adapter.controllers.HolderController
import com.boltic28.taskmanager.ui.adapter.controllers.IdeaSmallViewController
import com.boltic28.taskmanager.ui.adapter.controllers.TaskSmallViewController
import com.boltic28.taskmanager.ui.screens.goalfragment.GoalFragmentModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Named

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
                override fun onActionButtonClick(item: Any) {}
                override fun onViewClick(item: Any) {}
            }
        )

    @Provides
    @IntoMap
    @ViewModelKey(KeyFragmentModel::class)
    fun provideViewModel(vm: KeyFragmentModel): ViewModel = vm
}