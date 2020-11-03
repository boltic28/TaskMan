package com.boltic28.taskmanager.ui.screens

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.di.ViewModelKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class MainActivityModule {

    @Provides
    @IntoMap
    @ViewModelKey(MainActivityModel::class)
    fun provideViewModel(vm: MainActivityModel): ViewModel = vm
}