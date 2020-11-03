package com.boltic28.taskmanager.di

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import com.boltic28.taskmanager.signtools.FireUserManager
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.di.ActivityScope
import com.boltic28.taskmanager.utils.Messenger
import com.boltic28.taskmanager.utils.NetworkChecker
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class AppModule(private val app: App) {

    @Provides
    fun provideMessenger(): Messenger =
        Messenger(app)

    @Provides
    fun provideNetworkChecker(): NetworkChecker =
        NetworkChecker(app)

    @Provides
    fun provideApplication(): App = app

    @Provides
    fun providesViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory = factory

    @Provides
    fun provideFirebaseManager (activity: Activity): UserManager =
        FireUserManager(activity, provideMessenger())
}
