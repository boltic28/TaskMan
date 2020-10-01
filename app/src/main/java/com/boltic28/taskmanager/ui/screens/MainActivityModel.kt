package com.boltic28.taskmanager.ui.screens

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.signtools.FireUserManager

class MainActivityModel : ViewModel() {

    lateinit var userManager: FireUserManager
}