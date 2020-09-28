package com.boltic28.taskmanager.daggermain

import android.content.Context
import com.boltic28.taskmanager.utils.Messenger
import com.boltic28.taskmanager.utils.NetworkChecker
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideMessenger(): Messenger =
        Messenger(context)

    @Singleton
    @Provides
    fun provideNetworkChecker(): NetworkChecker =
        NetworkChecker(context)
}