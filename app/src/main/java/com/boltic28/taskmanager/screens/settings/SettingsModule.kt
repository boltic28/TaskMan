package com.boltic28.taskmanager.screens.settings

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SettingsModule(private val context: Context) {

    private val preferences = "task_man_pref"

    @Singleton
    @Provides
    fun providePreferences(): SharedPreferences =
        context.getSharedPreferences(preferences, Context.MODE_PRIVATE)
}