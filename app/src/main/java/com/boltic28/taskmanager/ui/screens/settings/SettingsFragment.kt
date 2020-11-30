package com.boltic28.taskmanager.ui.screens.settings

import android.app.TimePickerDialog
import android.content.ContentResolver
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.navigation.fragment.findNavController
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.businesslayer.syncmanager.AUTHORITY
import com.boltic28.taskmanager.ui.base.BaseFragment
import com.boltic28.taskmanager.ui.constant.*
import com.boltic28.taskmanager.ui.screens.activity.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_settings.*
import java.time.LocalTime

class SettingsFragment : BaseFragment<SettingsFragmentModel>(R.layout.fragment_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarText(resources.getString(R.string.setting_header))

        if (model.userManager.isUserSigned()) {
            mAccount = createSyncAccount()
            initSyncSettings()
        }

        initThemeChooser()
        initLanguage()
        initNotifyTime()
        initUser()

        setting_user_sign_out.setOnClickListener {
            model.userManager.signOut()
            model.clearUserData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ ->
                    findNavController().navigate(R.id.signFragment)
                }
        }

        setting_button_save.setOnClickListener {
            saveTheme()
            saveLang()
        }
    }

    private fun initUser() {
        model.disposables + model.userManager.user.subscribe {
            setting_user_value.text = it.email
        }
    }

    private fun initThemeChooser() {
        when (model.preferences.getString(APP_SET_THEME, THEME_BLUE)) {
            THEME_LIME -> {
                setting_theme_value.check(R.id.settings_lime_theme)
            }
            THEME_PINK -> {
                setting_theme_value.check(R.id.settings_pink_theme)
            }
            else -> {
                setting_theme_value.check(R.id.settings_blue_theme)
            }
        }
    }

    private fun saveTheme() {
        when (setting_theme_value.checkedRadioButtonId) {
            R.id.settings_lime_theme -> {
                activity?.setTheme(R.style.Lime)
                model.writeThemeInPreference(THEME_LIME)
            }
            R.id.settings_pink_theme -> {
                activity?.setTheme(R.style.Pink)
                model.writeThemeInPreference(THEME_PINK)
            }
            R.id.settings_blue_theme -> {
                activity?.setTheme(R.style.Blue)
                model.writeThemeInPreference(THEME_BLUE)
            }
        }
    }

    private fun initLanguage() {
        when (model.preferences.getString(APP_SET_LANG, ENGLISH)) {
            ENGLISH -> {
                setting_language_value.check(R.id.settings_english_lang)
            }
            RUSSIAN -> {
                setting_language_value.check(R.id.settings_russian_lang)
            }
            else -> {
                setting_language_value.check(R.id.settings_english_lang)
            }
        }
    }

    private fun saveLang() {
        when (setting_language_value.checkedRadioButtonId) {
            R.id.settings_english_lang -> {
                model.writeLangInPreference(ENGLISH)
                startActivity(Intent(context, MainActivity::class.java))
            }
            R.id.settings_russian_lang -> {
                model.writeLangInPreference(RUSSIAN)
                startActivity(Intent(context, MainActivity::class.java))
            }
        }
    }

    private fun initNotifyTime() {
        var notifyTime =
            model.preferences.getString(APP_SET_NOTIFY_TIME, APP_DEF_TIME_NOTIFY)
                ?: APP_DEF_TIME_NOTIFY

        setting_notify_time_value.text = notifyTime
        setting_notify_time_value.setOnClickListener {
            val listener = TimePickerDialog.OnTimeSetListener { _, h, m ->
                notifyTime = LocalTime.of(h, m).toString()
                setting_notify_time_value.text = notifyTime
                model.writeNotifyTimeInPreference(notifyTime)
            }
            val timePicker = TimePickerDialog(
                requireContext(),
                listener,
                9,
                0,
                true
            )
            timePicker.show()
        }
    }

    private fun initSyncSettings() {
        setting_sync_time_header.visibility = View.VISIBLE
        setting_sync_time_value.visibility = View.VISIBLE
        setting_button_sync_now.visibility = View.VISIBLE
        setting_sync_time_value.isChecked = model.preferences.getBoolean(APP_SET_SYNC_AUTO, APP_DEF_SYNC_AUTO_VAL)
        setting_sync_time_value.setOnCheckedChangeListener { _, isChecked ->
            model.writeAutoSyncStatus(isChecked)
        }
        setting_button_sync_now.setOnClickListener {
            ContentResolver.requestSync(mAccount, AUTHORITY, Bundle())
            showProgressBar()
            Handler(requireContext().mainLooper).postDelayed({hideProgressBar()}, 5000)
        }
    }
}