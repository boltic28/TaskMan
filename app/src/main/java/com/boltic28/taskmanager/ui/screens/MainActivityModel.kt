package com.boltic28.taskmanager.ui.screens

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.signtools.FireUserManager
import io.reactivex.disposables.Disposable

class MainActivityModel : ViewModel() {

    lateinit var userManager: FireUserManager
}