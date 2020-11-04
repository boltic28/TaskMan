package com.boltic28.taskmanager.ui.screens.sign

import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.base.BaseViewModel
import com.boltic28.taskmanager.utils.Messenger
import javax.inject.Inject

class SignFragmentModel @Inject constructor(
    override var userManager: UserManager,
    val messenger: Messenger
): BaseViewModel()