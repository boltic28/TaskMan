package com.boltic28.taskmanager.ui

import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.ui.constant.*

fun setPlaceHolderForItem(icon: String): Int =
    when (icon) {
        GOAL_EXTRA -> R.drawable.goal_ph
        STEP_EXTRA -> R.drawable.step_ph
        TASK_EXTRA -> R.drawable.task_ph
        IDEA_EXTRA -> R.drawable.idea_ph
        KEY_EXTRA -> R.drawable.key_ph
        else -> R.drawable.undf_ph
    }