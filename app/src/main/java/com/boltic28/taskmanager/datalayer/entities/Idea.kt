package com.boltic28.taskmanager.datalayer.entities

import java.time.LocalDateTime

data class Idea(
    val id: Long,
    val stepId: Long,
    val keyId: Long,
    val goalId: Long,
    val name: String,
    val description: String,
    val icon: String,
    val date: LocalDateTime
)