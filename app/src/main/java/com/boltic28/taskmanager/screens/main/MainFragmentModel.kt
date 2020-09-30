package com.boltic28.taskmanager.screens.main

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.datalayer.classes.Goal
import com.boltic28.taskmanager.datalayer.room.goal.DefaultGoalService
import com.boltic28.taskmanager.signtools.FireUserManager
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class MainFragmentModel: ViewModel() {

    @Inject
    lateinit var goalService: DefaultGoalService

    lateinit var userManager: FireUserManager

    init {

    }
}