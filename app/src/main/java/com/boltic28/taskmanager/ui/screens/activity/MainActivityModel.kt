package com.boltic28.taskmanager.ui.screens.activity

import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.base.BaseViewModel
import javax.inject.Inject

class MainActivityModel @Inject constructor(
    override var userManager: UserManager
) : BaseViewModel() {
}