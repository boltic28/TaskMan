package com.boltic28.taskmanager.presentation

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.signin.FireUserManager

class MainFragmentModel: ViewModel() {

    lateinit var userManager: FireUserManager
}