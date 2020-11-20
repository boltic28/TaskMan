package com.boltic28.taskmanager.datalayer.firebaseworker.dto.step

import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.datalayer.firebaseworker.FireBaseDatabase
import com.boltic28.taskmanager.datalayer.firebaseworker.RemoteDB
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.RemoteRepo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class StepRemoteRepo(
    private val repository: RemoteDB
): RemoteRepo<Step> {

    private val _mStep: BehaviorSubject<Step> = BehaviorSubject.create()
    val step: Observable<Step>
        get() = _mStep

    override fun create(item: Step) {
        repository.getRepositoryForUser().child(FireBaseDatabase.TABLE_STEP)
            .child(item.uid)
            .setValue(item.toRemoteObject())
    }

    override fun readByUid(uid: String): Observable<Step> {
        repository.getRepositoryForUser().child(FireBaseDatabase.TABLE_STEP).child(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children
                        .forEach { data ->
                            data.getValue(RemoteStep::class.java)?.let { dbGoal ->
                                _mStep.onNext(dbGoal.toLocalObject())
                            }
                        }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        return step
    }

    override fun readAll(): Observable<Step> {
        repository.getRepositoryForUser().child(FireBaseDatabase.TABLE_STEP)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children
                        .forEach { data ->
                            data.getValue(RemoteStep::class.java)?.let { dbGoal ->
                                _mStep.onNext(dbGoal.toLocalObject())
                            }
                        }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        return step
    }

    override fun delete(item: Step) {
        repository.getRepositoryForUser().child(FireBaseDatabase.TABLE_STEP)
            .child(item.uid)
            .removeValue()
    }
}