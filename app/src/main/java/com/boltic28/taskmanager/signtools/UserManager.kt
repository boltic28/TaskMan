package com.boltic28.taskmanager.signtools

import io.reactivex.Observable

interface UserManager {

    var userI: UserIn
    val user: Observable<UserIn>

    fun create(email: String, password: String)
    fun signIn(email: String, password: String)
    fun signOut()
    fun isUserSigned(): Boolean
}