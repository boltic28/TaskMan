package com.boltic28.taskmanager.datalayer.dto

import java.time.LocalDateTime

data class Idea(
    val id: Long,
    val name: String,
    val icon: String,
    val date: LocalDateTime
)