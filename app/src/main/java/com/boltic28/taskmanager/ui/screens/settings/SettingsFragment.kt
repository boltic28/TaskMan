package com.boltic28.taskmanager.ui.screens.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.ui.base.BaseFragment
import com.boltic28.taskmanager.ui.constant.*
import com.boltic28.taskmanager.ui.screens.activity.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment<SettingsFragmentModel>(R.layout.fragment_settings) {

    private var disposable: Disposable = Disposables.disposed()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarText(resources.getString(R.string.setting_header))

        initThemeChooser()
        initLanguage()
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

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.dispose()
    }

    private fun initUser(){
        disposable = model.userManager.user.subscribe {
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
}