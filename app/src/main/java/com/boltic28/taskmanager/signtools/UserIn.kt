package com.boltic28.taskmanager.signtools

const val USER_DATA_ID = "user_id"
const val USER_DATA_NAME = "user_name"
const val USER_DATA_EMAIL = "user_email"
const val USER_DATA_PHOTO_URL = "user_photo"
const val USER_DATA_PASSWORD = "user_password"

data class UserIn(
    val id: String,
    val name: String,
    val email: String,
    val photoUrl: String,
    val password: String
)