package com.boltic28.taskmanager.datalayer.firebaseworker.dto.idea

import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.firebaseworker.FireBaseDatabase
import com.boltic28.taskmanager.datalayer.firebaseworker.FireBaseDatabase.Companion.TABLE_IDEA
import com.boltic28.taskmanager.datalayer.firebaseworker.RemoteDB
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.RemoteRepo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class IdeaRemoteRepo(
    private val repository: RemoteDB
): RemoteRepo<Idea> {

    private val _mIdea: BehaviorSubject<Idea> = BehaviorSubject.create()
    val idea: Observable<Idea>
        get() = _mIdea

    override fun create(item: Idea) {
        repository.getRepositoryForUser().child(TABLE_IDEA)
            .child(item.uid)
            .setValue(item.toRemoteObject())
    }

    override fun readByUid(uid: String): Observable<Idea> {
        repository.getRepositoryForUser().child(TABLE_IDEA).child(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children
                        .forEach { data ->
                            data.getValue(RemoteIdea::class.java)?.let { dbGoal ->
                                _mIdea.onNext(dbGoal.toLocalObject())
                            }
                        }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        return idea
    }

    override fun readAll(): Observable<Idea> {
        repository.getRepositoryForUser().child(TABLE_IDEA)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children
                        .forEach { data ->
                            data.getValue(RemoteIdea::class.java)?.let { dbGoal ->
                                _mIdea.onNext(dbGoal.toLocalObject())
                            }
                        }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        return idea
    }

    override fun delete(item: Idea) {
        repository.getRepositoryForUser().child(TABLE_IDEA)
            .child(item.uid)
            .removeValue()
    }
}