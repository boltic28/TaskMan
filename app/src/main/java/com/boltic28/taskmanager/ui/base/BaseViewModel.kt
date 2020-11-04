package com.boltic28.taskmanager.ui.base

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.signtools.UserManager

abstract class BaseViewModel: ViewModel(){

    abstract var userManager: UserManager
}