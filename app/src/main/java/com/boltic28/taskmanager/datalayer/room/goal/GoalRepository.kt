package com.boltic28.taskmanager.datalayer.room.goal

import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.room.Crud
import io.reactivex.Single

interface GoalRepository: Crud<Goal>