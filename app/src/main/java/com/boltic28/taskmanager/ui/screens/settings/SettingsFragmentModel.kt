package com.boltic28.taskmanager.ui.screens.settings

import android.content.SharedPreferences
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.base.BaseViewModel
import com.boltic28.taskmanager.utils.Messenger
import javax.inject.Inject

class SettingsFragmentModel @Inject constructor(
    override var userManager: UserManager,
    val preferences: SharedPreferences,
    val messenger: Messenger
    ): BaseViewModel()