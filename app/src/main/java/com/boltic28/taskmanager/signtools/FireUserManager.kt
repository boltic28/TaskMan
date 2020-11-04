package com.boltic28.taskmanager.signtools

import android.app.Activity
import android.util.Log
import com.boltic28.taskmanager.utils.Messenger
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class FireUserManager(
    private val activity: Activity,
    private val messenger: Messenger
) :
    UserManager {

    companion object {
        const val CREATE = "create"
        const val SIGN_IN = "signIn"
        const val EMPTY_STRING = ""
    }

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val userSubject = BehaviorSubject.createDefault<UserIn>(convertUser(mAuth.currentUser))
    override val user: Observable<UserIn>
        get() = userSubject.hide()

    init {

        mAuth.addAuthStateListener {
            val person = mAuth.currentUser
            if (person == null) {
                userSubject.onNext(convertUser(person))
            }
        }
    }

    override fun create(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) {
                checkTaskAndSetUser(
                    it,
                    CREATE
                )
            }
    }

    override fun signIn(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) {
                checkTaskAndSetUser(
                    it,
                    SIGN_IN
                )
            }
    }

    override fun signOut() = mAuth.signOut()

    override fun isUserSigned(): Boolean =
        convertUser(mAuth.currentUser).id != EMPTY_STRING

    private fun convertUser(user: FirebaseUser?): UserIn =
        if (user != null) {
            UserIn(
                user.uid,
                user.displayName ?: EMPTY_STRING,
                user.email ?: EMPTY_STRING,
                user.photoUrl.toString()
            )
        } else {
            UserIn(
                EMPTY_STRING,
                EMPTY_STRING,
                EMPTY_STRING,
                EMPTY_STRING
            )
        }

    private fun checkTaskAndSetUser(task: Task<AuthResult>, type: String) {
        if (task.isSuccessful) {
            messenger.showMessage("$type successful")
        } else {
            messenger.showMessage("$type failed")
        }
        userSubject.onNext(convertUser(mAuth.currentUser))
    }
}