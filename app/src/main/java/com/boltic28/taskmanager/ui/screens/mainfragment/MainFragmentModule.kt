package com.boltic28.taskmanager.ui.screens.mainfragment

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.datalayer.entities.BaseItem
import com.boltic28.taskmanager.ui.di.ViewModelKey
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.*
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

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
                override fun isNeedToShowConnection(): Boolean = true
                override fun onActionButtonClick(item: BaseItem) {}
                override fun onViewClick(item: BaseItem) {}
            }
        )

    @Provides
    @IntoMap
    @ViewModelKey(MainFragmentModel::class)
    fun provideViewModel(vm: MainFragmentModel): ViewModel = vm
}