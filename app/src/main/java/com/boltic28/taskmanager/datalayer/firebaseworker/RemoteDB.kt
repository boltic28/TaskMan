package com.boltic28.taskmanager.datalayer.firebaseworker

import com.google.firebase.database.DatabaseReference

interface RemoteDB {

    fun getRepositoryForUser(): DatabaseReference
}