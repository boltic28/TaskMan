package com.boltic28.taskmanager.datalayer.dto

import java.time.LocalDateTime

data class User(
    val id:Long,
    val name: String,
    val password: String,
    val registrationDate: LocalDateTime,
    val mail: String,
    val phone: String,
    val photo: String,
    val dob: LocalDateTime
)