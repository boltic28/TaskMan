package com.boltic28.taskmanager.ui.screens.sign

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.di.ViewModelKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class SignFragmentModule {

    @Provides
    @IntoMap
    @ViewModelKey(SignFragmentModel::class)
    fun provideViewModel(vm: SignFragmentModel): ViewModel = vm
}