package com.boltic28.taskmanager.datalayer.dto

import java.time.LocalDateTime

class Step(
    var id: Long,
    var ownerId: Long,
    var keyId: Long,
    var name: String,
    var description: String,
    var icon: String,
    var date: LocalDateTime,
    var dateClose: LocalDateTime,
    var isDone: Boolean,
    var isStarted: Boolean
)