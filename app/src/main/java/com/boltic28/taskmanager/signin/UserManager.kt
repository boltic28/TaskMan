package com.boltic28.taskmanager.signin

interface UserManager<T> {

    fun create(email: String, password: String)
    fun signIn(email: String, password: String)
    fun signOut()
    fun convertUser(user: T): UserIn
}