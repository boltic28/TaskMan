package com.boltic28.taskmanager.datalayer.entities

import java.time.LocalDateTime

data class Idea(
    override val id: Long,
    override val name: String,
    override val description: String,
    override val icon: String,
    override val date: LocalDateTime,
    override val dateClose: LocalDateTime,
    val stepId: Long,
    val keyId: Long,
    val goalId: Long
): BaseItem