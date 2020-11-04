package com.boltic28.taskmanager.ui.screens.mainfragment

import com.boltic28.taskmanager.businesslayer.FreeElementsInteractor
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.base.BaseViewModel
import com.boltic28.taskmanager.utils.Messenger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainFragmentModel @Inject constructor(
    private val interactor: FreeElementsInteractor,
    val adapter: ItemAdapter,
    private val messenger: Messenger,
    override var userManager: UserManager
) : BaseViewModel() {

    val disposables = mutableListOf<Disposable>()

    fun loadTasks() {
        disposables + interactor.getFreeKeys()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                adapter.refreshData(result)
            }, {
            })
    }

    fun loadKeys() {
        disposables + interactor.getFreeTasks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                adapter.refreshData(result)
            }, {
            })
    }

    fun loadIdeas() {
        disposables + interactor.getFreeIdeas()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                adapter.refreshData(result)
            }, {
            })
    }

    fun loadSteps() {
        disposables + interactor.getFreeSteps()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                adapter.refreshData(result)
            }, {
            })
    }

    fun loadGoals() {
        disposables + interactor.getGoals()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ goalsList ->
                adapter.clearAll()
                goalsList.forEach { goal ->
                    makeAnalyzeAndPushIntoAdapter(goal)
                }
            }, {
            })
    }

    private fun makeAnalyzeAndPushIntoAdapter(goal: Goal) {
        disposables + interactor.setChildrenFor(goal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapter.addElement(interactor.setProgressFor(it))
            }, {
            })
    }
}