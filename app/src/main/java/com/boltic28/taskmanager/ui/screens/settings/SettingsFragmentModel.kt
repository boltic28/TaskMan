package com.boltic28.taskmanager.ui.screens.settings

import android.content.SharedPreferences
import com.boltic28.taskmanager.businesslayer.usecases.RefreshDataUseCase
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.base.BaseViewModel
import com.boltic28.taskmanager.ui.constant.APP_SET_LANG
import com.boltic28.taskmanager.ui.constant.APP_SET_NOTIFY_TIME
import com.boltic28.taskmanager.ui.constant.APP_SET_THEME
import com.boltic28.taskmanager.utils.Messenger
import io.reactivex.Single
import javax.inject.Inject

class SettingsFragmentModel @Inject constructor(
    override var userManager: UserManager,
    val preferences: SharedPreferences,
    val messenger: Messenger,
    private val database: RefreshDataUseCase
    ): BaseViewModel() {

    fun clearUserData(): Single<Int> =
        database.clearLocalData()

    fun writeThemeInPreference(theme: String){
        preferences.edit().putString(APP_SET_THEME, theme).apply()
    }

    fun writeLangInPreference(lang: String){
        preferences.edit().putString(APP_SET_LANG, lang).apply()
    }

    fun writeNotifyTimeInPreference(time: String){
        preferences.edit().putString(APP_SET_NOTIFY_TIME, time).apply()
    }
}