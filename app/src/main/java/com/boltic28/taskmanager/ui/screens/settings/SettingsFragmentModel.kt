package com.boltic28.taskmanager.ui.screens.settings

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.di.App
import javax.inject.Inject

class SettingsFragmentModel: ViewModel() {

    @Inject
    lateinit var preferences: SharedPreferences

    init {
        App.component.injectModel(this)
    }
}