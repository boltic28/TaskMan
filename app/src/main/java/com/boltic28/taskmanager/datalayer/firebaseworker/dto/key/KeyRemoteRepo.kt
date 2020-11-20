package com.boltic28.taskmanager.datalayer.firebaseworker.dto.key

import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.firebaseworker.FireBaseDatabase.Companion.TABLE_KEY
import com.boltic28.taskmanager.datalayer.firebaseworker.RemoteDB
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.RemoteRepo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class KeyRemoteRepo(
    private val repository: RemoteDB
): RemoteRepo<KeyResult> {

    private val _mKey: BehaviorSubject<KeyResult> = BehaviorSubject.create()
    val key: Observable<KeyResult>
        get() = _mKey

    override fun create(item: KeyResult) {
        repository.getRepositoryForUser().child(TABLE_KEY)
            .child(item.uid)
            .setValue(item.toRemoteObject())
    }

    override fun readByUid(uid: String): Observable<KeyResult> {
        repository.getRepositoryForUser().child(TABLE_KEY).child(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children
                        .forEach { data ->
                            data.getValue(RemoteKey::class.java)?.let { dbGoal ->
                                _mKey.onNext(dbGoal.toLocalObject())
                            }
                        }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        return key
    }

    override fun readAll(): Observable<KeyResult> {
        repository.getRepositoryForUser().child(TABLE_KEY)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children
                        .forEach { data ->
                            data.getValue(RemoteKey::class.java)?.let { dbGoal ->
                                _mKey.onNext(dbGoal.toLocalObject())
                            }
                        }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        return key
    }

    override fun delete(item: KeyResult) {
        repository.getRepositoryForUser().child(TABLE_KEY)
            .child(item.uid)
            .removeValue()
    }
}