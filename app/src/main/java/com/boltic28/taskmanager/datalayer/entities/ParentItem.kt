package com.boltic28.taskmanager.datalayer.entities

import com.boltic28.taskmanager.datalayer.Progress
import java.time.LocalDateTime

interface ParentItem: BaseItem {
    val isStarted: Boolean
    val isDone: Boolean
    val progress: Progress
}