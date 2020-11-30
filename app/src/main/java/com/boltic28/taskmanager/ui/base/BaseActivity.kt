package com.boltic28.taskmanager.ui.base

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.businesslayer.service.NotifyService
import com.boltic28.taskmanager.di.App
import com.boltic28.taskmanager.ui.constant.*
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel>(private val layout: Int) : FragmentActivity(),
    HasAndroidInjector {

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
        setAlarmManager()
    }

    override fun androidInjector(): AndroidInjector<Any> = fragmentInjector

    private fun setAlarmManager() {
        val remindTime = LocalTime.parse(
            getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).getString(
                APP_SET_NOTIFY_TIME, APP_DEF_TIME_NOTIFY
            )
        )
        val alarmManager: AlarmManager = getSystemService(AlarmManager::class.java)

        val isNextDay = LocalTime.now() > remindTime
        val alarmTime = if (isNextDay) {
            LocalDateTime.of(LocalDate.now().plusDays(1), remindTime)
        } else {
            LocalDateTime.of(LocalDate.now(), remindTime)
        }.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val intent = Intent(this, NotifyService::class.java)
        intent.action = NotifyService.ACTION_CHECK_TASKS

        val pIntent = PendingIntent.getService(this, 0, intent, 0)

        alarmManager.cancel(pIntent)
        alarmManager.set(AlarmManager.RTC, alarmTime, pIntent)
    }

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
