package com.boltic28.taskmanager.signtools

import android.app.Activity
import android.util.Log
import com.boltic28.taskmanager.daggermain.AppDagger
import com.boltic28.taskmanager.screens.MainActivity.Companion.TAG
import com.boltic28.taskmanager.utils.Messenger
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class FireUserManager(private val activity: Activity) :
    UserManager<FirebaseUser?> {

    companion object {
        const val CREATE = "create"
        const val SIGN_IN = "signIn"
        const val EMPTY_STRING = ""
        private var manager: FireUserManager? = null

        fun getInstance(activity: Activity): FireUserManager {
            if (manager == null) {
                manager =
                    FireUserManager(activity)
                AppDagger.component.injectManager(manager!!)
            }
            return manager!!
        }
    }

    @Inject
    lateinit var messenger: Messenger

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val userSubject = BehaviorSubject.createDefault<UserIn>(convertUser(mAuth.currentUser))
    val user: Observable<UserIn>
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

    override fun signOut() {
        mAuth.signOut()
        Log.d(TAG, "user is signed Out")
    }

    override fun convertUser(user: FirebaseUser?): UserIn =
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
            Log.d(TAG, "$type : success")
            messenger.showMessage("$type successful")
        } else {
            Log.d(TAG, "$type : failed")
            messenger.showMessage("$type failed")
        }
        userSubject.onNext(convertUser(mAuth.currentUser))
    }
}