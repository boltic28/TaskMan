package com.boltic28.taskmanager.datalayer.dto

import java.time.LocalDateTime

class Idea(
    var id: Long,
    var stepId: Long,
    var keyId: Long,
    var goalId: Long,
    var name: String,
    var description: String,
    var icon: String,
    var date: LocalDateTime
)