package com.boltic28.taskmanager.ui.screens.taskfragment

import androidx.navigation.NavController
import com.boltic28.taskmanager.businesslayer.TaskFragmentInteractor
import com.boltic28.taskmanager.datalayer.Cycle
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.HolderController
import com.boltic28.taskmanager.ui.base.BaseEntityFragmentModel
import com.boltic28.taskmanager.utils.Messenger
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.time.LocalDateTime
import javax.inject.Inject

class TaskFragmentModel @Inject constructor(
    @AdapterForTask
    val adapter: ItemAdapter,
    private val interactor: TaskFragmentInteractor,
    override var userManager: UserManager,
    val messenger: Messenger
) : BaseEntityFragmentModel<Task>() {

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

    fun isAttention(task: Task): Boolean {
        val isTimeOff = LocalDateTime.now() >= task.dateClose
        val isTaskDone = task.isDone
        val isTaskCycle = task.cycle != Cycle.NOT_CYCLE

        if (isTimeOff && isTaskDone && isTaskCycle) restartTask(task)
        if (isTimeOff && !isTaskDone) return true

        return false
    }

    fun getParentName(item: Task): Single<String> =
        interactor.getParentName(item)

    fun loadParentsElements(task: Task, nav: NavController) {
        adapter.setAdapterListener(object : HolderController.OnActionClickListener {
            override fun isNeedToShowConnection(): Boolean = false
            override fun onActionButtonClick(item: Any) {
                attachTo(task, item)
            }

            override fun onViewClick(item: Any) {
                goToItemFragment(item, nav)
            }
        })
        disposables + interactor.getFreeStepsGoalsKeys()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                adapter.refreshData(list)
            }
    }

    private fun restartTask(task: Task) {
        update(
            task.copy(
                date = task.dateClose,
                dateClose = Cycle.increasedPeriod(task),
                isDone = false,
                isStarted = false
            )
        )
    }

    private fun attachTo(task: Task, item: Any) {
        if (item is Step) {
            disposables + interactor.update(task.copy(stepId = item.id))
                .subscribeOn(Schedulers.io())
                .subscribe { _ -> refresh() }
        }
        if (item is Goal) {
            disposables + interactor.update(task.copy(goalId = item.id))
                .subscribeOn(Schedulers.io())
                .subscribe { _ -> refresh() }
        }
        if (item is KeyResult) {
            disposables + interactor.update(task.copy(keyId = item.id))
                .subscribeOn(Schedulers.io())
                .subscribe { _ -> refresh() }
        }
    }
}