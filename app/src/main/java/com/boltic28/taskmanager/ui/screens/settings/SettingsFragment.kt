package com.boltic28.taskmanager.ui.screens.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.di.AppDagger
import com.boltic28.taskmanager.utils.Messenger
import javax.inject.Inject

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    @Inject
    lateinit var messenger: Messenger

    val model: SettingsFragmentModel by lazy {
        ViewModelProviders.of(this).get(SettingsFragmentModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppDagger.component.injectFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}