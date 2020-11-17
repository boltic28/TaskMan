package com.boltic28.taskmanager.datalayer.firebaseworker.dto.task

import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.datalayer.firebaseworker.FireBaseDatabase
import com.boltic28.taskmanager.datalayer.firebaseworker.RemoteDB
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.RemoteRepo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class TaskRemoteRepo(
    private val repository: RemoteDB
) : RemoteRepo<Task> {

    private val _mTask: BehaviorSubject<Task> = BehaviorSubject.create()
    val task: Observable<Task>
        get() = _mTask

    override fun create(item: Task) {
        repository.getRepositoryForUser().child(FireBaseDatabase.TABLE_TASK)
            .child(item.uid)
            .setValue(item.toRemoteObject())
    }

    override fun readByUid(uid: String): Observable<Task> {
        repository.getRepositoryForUser().child(FireBaseDatabase.TABLE_TASK).child(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children
                        .forEach { data ->
                            data.getValue(RemoteTask::class.java)?.let { dbGoal ->
                                _mTask.onNext(dbGoal.toLocalObject())
                            }
                        }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        return task
    }

    override fun readAll(): Observable<Task> {
        repository.getRepositoryForUser().child(FireBaseDatabase.TABLE_TASK)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children
                        .forEach { data ->
                            data.getValue(RemoteTask::class.java)?.let { dbGoal ->
                                _mTask.onNext(dbGoal.toLocalObject())
                            }
                        }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        return task
    }

    override fun delete(item: Task) {
        repository.getRepositoryForUser().child(FireBaseDatabase.TABLE_TASK)
            .child(item.uid)
            .removeValue()
    }
}