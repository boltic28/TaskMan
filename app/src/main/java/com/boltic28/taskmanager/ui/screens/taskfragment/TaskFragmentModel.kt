package com.boltic28.taskmanager.ui.screens.taskfragment

import com.boltic28.taskmanager.businesslayer.TaskFragmentInteractor
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.base.BaseEntityFragmentModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TaskFragmentModel @Inject constructor(
    @AdapterForTask
    val adapter: ItemAdapter,
    private val interactor: TaskFragmentInteractor,
    override var userManager: UserManager,
): BaseEntityFragmentModel<Task>() {

    override fun refresh() {
        disposables + interactor.getTaskById(itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it -> mItem.onNext(it) }
    }

    fun update(task: Task) {
        disposables + interactor.update(task)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _ -> refresh() }
    }
}