package com.boltic28.taskmanager.ui.screens.creator

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.ui.di.ViewModelKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class CreatorFragmentModule {

    @Provides
    @IntoMap
    @ViewModelKey(CreatorFragmentModel::class)
    fun provideViewModel(vm: CreatorFragmentModel): ViewModel = vm
}