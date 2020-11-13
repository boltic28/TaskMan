package com.boltic28.taskmanager.datalayer.entities

import java.time.LocalDateTime

interface BaseItem {
    val id: Long
    val name: String
    val description: String
    val icon: String
    val date: LocalDateTime
    val dateClose: LocalDateTime
}