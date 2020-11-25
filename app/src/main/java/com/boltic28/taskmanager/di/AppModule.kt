package com.boltic28.taskmanager.di

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import com.boltic28.taskmanager.datalayer.firebaseworker.FireBaseDatabase
import com.boltic28.taskmanager.datalayer.firebaseworker.RemoteDB
import com.boltic28.taskmanager.ui.constant.APP_PREFERENCES
import com.boltic28.taskmanager.ui.di.AppViewModelFactory
import com.boltic28.taskmanager.utils.Messenger
import com.boltic28.taskmanager.utils.NetworkChecker
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val app: App) {

    @Provides
    fun providePreferences(): SharedPreferences =
        app.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

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

    @Provides
    fun provideFBDataBase(): RemoteDB = FireBaseDatabase()
}
