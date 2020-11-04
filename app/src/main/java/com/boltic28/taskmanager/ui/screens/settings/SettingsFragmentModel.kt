package com.boltic28.taskmanager.ui.screens.settings

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.di.App
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.base.BaseViewModel
import java.util.*
import javax.inject.Inject

class SettingsFragmentModel @Inject constructor(
    override var userManager: UserManager,
    private val preferences: SharedPreferences
    ): BaseViewModel()