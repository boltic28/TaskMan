package com.boltic28.taskmanager.di

import android.content.Context
import com.boltic28.taskmanager.utils.Messenger
import com.boltic28.taskmanager.utils.NetworkChecker
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    fun provideMessenger(): Messenger =
        Messenger(context)

    @Provides
    fun provideNetworkChecker(): NetworkChecker =
        NetworkChecker(context)
}