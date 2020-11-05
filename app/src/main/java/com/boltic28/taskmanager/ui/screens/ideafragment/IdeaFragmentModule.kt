package com.boltic28.taskmanager.ui.screens.ideafragment

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.ui.di.ViewModelKey
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.*
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class IdeaFragmentModule {

    @AdapterForIdea
    @Provides
    fun provideAdapter(): ItemAdapter =
        ItemAdapter(
            listOf(
                TaskSmallViewController(),
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
    @ViewModelKey(IdeaFragmentModel::class)
    fun provideViewModel(vm: IdeaFragmentModel): ViewModel = vm
}