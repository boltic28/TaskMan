package com.boltic28.taskmanager.signtools

import android.os.Bundle
import io.reactivex.Observable

interface UserManager {

    var userI: UserIn
    val user: Observable<UserIn>
    var password: String

    fun create(email: String, password: String)
    fun signIn(email: String, password: String)
    fun signOut()
    fun isUserSigned(): Boolean

    fun getUserData(): Bundle
}