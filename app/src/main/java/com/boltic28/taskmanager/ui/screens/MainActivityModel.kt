package com.boltic28.taskmanager.ui.screens

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.signtools.FireUserManager
import com.boltic28.taskmanager.signtools.UserManager
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainActivityModel @Inject constructor(
    val userManager: UserManager
) : ViewModel() {
}