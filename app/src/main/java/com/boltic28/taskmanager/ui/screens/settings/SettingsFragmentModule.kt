package com.boltic28.taskmanager.ui.screens.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.di.AppScope
import com.boltic28.taskmanager.di.ViewModelKey
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragmentModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class SettingsFragmentModule(private val context: Context) {

    private val preferences = "task_man_pref"

    @Provides
    fun providePreferences(): SharedPreferences =
        context.getSharedPreferences(preferences, Context.MODE_PRIVATE)

    @Provides
    @IntoMap
    @ViewModelKey(SettingsFragmentModel::class)
    fun provideViewModel(vm: SettingsFragmentModel): ViewModel = vm
}