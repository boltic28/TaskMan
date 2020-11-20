package com.boltic28.taskmanager.datalayer.entities

import com.boltic28.taskmanager.datalayer.Progress
import java.time.LocalDateTime

interface ProgressiveItem: CompletableItem {
    val progress: Progress
}