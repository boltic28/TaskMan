package com.boltic28.taskmanager.di

import androidx.lifecycle.ViewModelProvider
import com.boltic28.taskmanager.ui.di.AppViewModelFactory
import com.boltic28.taskmanager.utils.Messenger
import com.boltic28.taskmanager.utils.NetworkChecker
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val app: App) {

    @Provides
    fun provideApplication(): App = app

    @Provides
    fun providesViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory = factory

    @Provides
    fun provideMessenger(): Messenger =
        Messenger(app)

    @Provides
    fun provideNetworkChecker(): NetworkChecker =
        NetworkChecker(app)

}
