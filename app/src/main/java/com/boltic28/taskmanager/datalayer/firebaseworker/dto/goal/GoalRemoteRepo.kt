package com.boltic28.taskmanager.datalayer.firebaseworker.dto.goal

import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.firebaseworker.FireBaseDatabase.Companion.TABLE_GOAL
import com.boltic28.taskmanager.datalayer.firebaseworker.RemoteDB
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.RemoteRepo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class GoalRemoteRepo(
    private val repository: RemoteDB
) : RemoteRepo<Goal> {

    private val _mGoal: BehaviorSubject<Goal> = BehaviorSubject.create()
    val goal: Observable<Goal>
        get() = _mGoal

    override fun create(item: Goal) {
            repository.getRepositoryForUser().child(TABLE_GOAL)
                .child(item.uid)
                .setValue(item.toRemoteObject())
    }

    override fun delete(item: Goal) {
        repository.getRepositoryForUser().child(TABLE_GOAL)
            .child(item.uid)
            .removeValue()
    }

    override fun readByUid(uid: String): Observable<Goal> {
        repository.getRepositoryForUser().child(TABLE_GOAL).child(uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children
                            .forEach { data ->
                                data.getValue(RemoteGoal::class.java)?.let { dbGoal ->
                                    _mGoal.onNext(dbGoal.toLocalObject())
                                }
                            }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            return goal
        }

    override fun readAll(): Observable<Goal> {
        repository.getRepositoryForUser().child(TABLE_GOAL)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children
                            .forEach { data ->
                                data.getValue(RemoteGoal::class.java)?.let { dbGoal ->
                                    _mGoal.onNext(dbGoal.toLocalObject())
                                }
                            }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            return goal
        }
}