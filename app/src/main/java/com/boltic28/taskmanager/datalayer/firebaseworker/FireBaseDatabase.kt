package com.boltic28.taskmanager.datalayer.firebaseworker

import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.goal.RemoteGoal
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.goal.toLocalObject
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.goal.toRemoteObject
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.idea.RemoteIdea
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.idea.toLocalObject
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.idea.toRemoteObject
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.key.RemoteKey
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.key.toLocalObject
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.key.toRemoteObject
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.step.RemoteStep
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.step.toLocalObject
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.step.toRemoteObject
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.task.RemoteTask
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.task.toLocalObject
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.task.toRemoteObject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class FireBaseDatabase() : RemoteDB {

    companion object {
        const val TABLE_GOAL = "goals"
        const val TABLE_TASK = "tasks"
        const val TABLE_STEP = "steps"
        const val TABLE_IDEA = "ideas"
        const val TABLE_KEY = "keys"
    }

    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val firebaseUser by lazy{ FirebaseAuth.getInstance().currentUser}
    private val uid by lazy { firebaseUser!!.uid }
    private val db by lazy { firebaseDatabase.reference }

    private val _mGoal: BehaviorSubject<Goal> = BehaviorSubject.create()
    override val goal: Observable<Goal>
        get() = _mGoal

    private val _mStep: BehaviorSubject<Step> = BehaviorSubject.create()
    override val step: Observable<Step>
        get() = _mStep

    private val _mTask: BehaviorSubject<Task> = BehaviorSubject.create()
    override val task: Observable<Task>
        get() = _mTask

    private val _mIdea: BehaviorSubject<Idea> = BehaviorSubject.create()
    override val idea: Observable<Idea>
        get() = _mIdea

    private val _mKey: BehaviorSubject<KeyResult> = BehaviorSubject.create()
    override val key: Observable<KeyResult>
        get() = _mKey



    override fun write(item: Goal) {
        db.child(uid).child(TABLE_GOAL).child(item.name).setValue(item.toRemoteObject())
    }

    override fun write(item: Step) {
        db.child(uid).child(TABLE_STEP).child(item.name).setValue(item.toRemoteObject())
    }

    override fun write(item: Task) {
        db.child(uid).child(TABLE_TASK).child(item.name).setValue(item.toRemoteObject())
    }

    override fun write(item: Idea) {
        db.child(uid).child(TABLE_IDEA).child(item.name).setValue(item.toRemoteObject())
    }

    override fun write(item: KeyResult) {
        db.child(uid).child(TABLE_KEY).child(item.name).setValue(item.toRemoteObject())
    }

    override fun delete(item: Goal) {
        db.child(uid).child(TABLE_GOAL).child(item.name).removeValue()
    }

    override fun delete(item: Step) {
        db.child(uid).child(TABLE_STEP).child(item.name).removeValue()
    }

    override fun delete(item: Task) {
        db.child(uid).child(TABLE_TASK).child(item.name).removeValue()
    }

    override fun delete(item: Idea) {
        db.child(uid).child(TABLE_IDEA).child(item.name).removeValue()
    }

    override fun delete(item: KeyResult) {
        db.child(uid).child(TABLE_KEY).child(item.name).removeValue()
    }

    /**
     * Observers work once and always
     */

    override fun observeGoals() {
        db.child(uid).child(TABLE_GOAL).addValueEventListener(object : ValueEventListener {
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
    }

    override fun observeSteps() {
        db.child(uid).child(TABLE_STEP).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children
                    .forEach { data ->
                        data.getValue(RemoteStep::class.java)?.let { obj ->
                            _mStep.onNext(obj.toLocalObject())
                        }
                    }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun observeTasks() {
        db.child(uid).child(TABLE_TASK).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children
                    .forEach { data ->
                        data.getValue(RemoteTask::class.java)?.let { obj ->
                            _mTask.onNext(obj.toLocalObject())
                        }
                    }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun observeIdeas() {
        db.child(uid).child(TABLE_IDEA).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children
                    .forEach { data ->
                        data.getValue(RemoteIdea::class.java)?.let { obj ->
                            _mIdea.onNext(obj.toLocalObject())
                        }
                    }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun observeKeys() {
        db.child(uid).child(TABLE_KEY).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children
                    .forEach { data ->
                        data.getValue(RemoteKey::class.java)?.let { obj ->
                            _mKey.onNext(obj.toLocalObject())
                        }
                    }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun getRepositoryForUser(): DatabaseReference = db.child(uid)
}