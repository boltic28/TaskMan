package com.boltic28.taskmanager.datalayer.entities

interface CompletableItem: BaseItem {
    val isStarted: Boolean
    val isDone: Boolean
}