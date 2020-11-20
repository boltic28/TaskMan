package com.boltic28.taskmanager.datalayer.firebaseworker.dto

import io.reactivex.Observable

interface RemoteRepo<T> {

    fun create(item: T)

    fun readByUid(uid: String): Observable<T>

    fun readAll(): Observable<T>

    fun delete(item: T)
}