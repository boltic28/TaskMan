package com.boltic28.taskmanager.datalayer.dto

import java.time.LocalDateTime

class KeyResult(
    var id: Long,
    var goalId: Long,
    var name: String,
    var description: String,
    var date: LocalDateTime,
    var progress: Int
)