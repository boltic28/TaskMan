package com.boltic28.taskmanager.signtools

import io.reactivex.Observable

interface UserManager {
    val user: Observable<UserIn>
    fun create(email: String, password: String)
    fun signIn(email: String, password: String)
    fun signOut()
}