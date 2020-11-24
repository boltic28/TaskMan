package com.boltic28.taskmanager.datalayer.firebaseworker

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FireBaseDatabase() : RemoteDB {

    companion object {
        const val TABLE_GOAL = "goals"
        const val TABLE_TASK = "tasks"
        const val TABLE_STEP = "steps"
        const val TABLE_IDEA = "ideas"
        const val TABLE_KEY = "keys"
    }

    override fun getRepositoryForUser(): DatabaseReference =
        FirebaseDatabase.getInstance().reference.child(FirebaseAuth.getInstance().currentUser!!.uid)
}