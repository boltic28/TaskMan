package com.boltic28.taskmanager.screens.main

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.signtools.FireUserManager

class MainFragmentModel: ViewModel() {

    lateinit var userManager: FireUserManager
}