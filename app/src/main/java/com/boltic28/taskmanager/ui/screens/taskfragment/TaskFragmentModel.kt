package com.boltic28.taskmanager.ui.screens.taskfragment

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.businesslayer.TaskFragmentInteractor
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.di.App
import com.boltic28.taskmanager.signtools.UserManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class TaskFragmentModel: ViewModel() {

    @Inject
    lateinit var interactor: TaskFragmentInteractor

    lateinit var userManager: UserManager

    private val mStep = BehaviorSubject.create<Task>()
    val step: Observable<Task>
        get() = mStep.hide()

    var taskId = 0L

    val disposables = mutableListOf<Disposable>()

    init {
        App.taskComponent.inject(this)
    }

    fun refresh() {
        disposables + interactor.getTaskById(taskId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it -> mStep.onNext(it) }
    }

    fun update(task: Task) {
        disposables + interactor.update(task)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _ -> refresh() }
    }
}