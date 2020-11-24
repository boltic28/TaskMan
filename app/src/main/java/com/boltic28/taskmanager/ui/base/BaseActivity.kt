package com.boltic28.taskmanager.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.di.App
import com.boltic28.taskmanager.ui.constant.*
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

abstract class BaseActivity<VM : ViewModel>(private val layout: Int)
    : FragmentActivity(), HasAndroidInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var model: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).tryInjectActivity(this)
        loadPreferences()

        super.onCreate(savedInstanceState)
        setContentView(layout)

        setActionBar(toolbar)
    }

    override fun androidInjector(): AndroidInjector<Any> = fragmentInjector

    private fun loadPreferences() {
        val preferences = application.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        when (preferences.getString(APP_SET_THEME, THEME_BLUE)) {
            THEME_LIME -> {
                setTheme(R.style.Lime)
            }
            THEME_PINK -> {
                setTheme(R.style.Pink)
            }
            else -> {
                setTheme(R.style.Blue)
            }
        }
        when (preferences.getString(APP_SET_LANG, ENGLISH)) {
            ENGLISH -> {
                setLanguage("en")
            }
            RUSSIAN -> {
                setLanguage("ru")
            }
            else -> {
                setLanguage("en")
            }
        }
    }

    private fun setLanguage(localeName: String) {
        val dm = resources.displayMetrics
        resources.configuration.setLocale(Locale(localeName))
        resources.updateConfiguration(resources.configuration, dm)
    }
}
