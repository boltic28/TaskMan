package com.boltic28.taskmanager.ui.screens.mainfragment

import com.boltic28.taskmanager.businesslayer.interactors.MainFragmentInteractor
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.datalayer.firebaseworker.RemoteDB
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.base.BaseViewModel
import com.boltic28.taskmanager.utils.Messenger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainFragmentModel @Inject constructor(
    private val interactor: MainFragmentInteractor,
    val adapter: ItemAdapter,
    val messenger: Messenger,
    override var userManager: UserManager,
    val dbHelper: RemoteDB
) : BaseViewModel() {

    val disposables = mutableListOf<Disposable>()

    fun observeRemote() {
        dbHelper.observeGoals()
    }

    fun update(item: Task){
        disposables + interactor.update(item)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ _ -> loadTasks() }
    }

    fun loadTasks() {
        disposables + interactor.getTasks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ result -> adapter.refreshData(result) }
    }

    fun loadIdeas() {
        disposables + interactor.getIdeas()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ result -> adapter.refreshData(result) }
    }

    fun loadKeys() {
        disposables + interactor.getKeys()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ itemList ->
                adapter.clearAll()
                itemList.forEach { item ->
                    makeAnalyzeAndPushIntoAdapter(item)
                }
            }
    }

    fun loadSteps() {
        disposables + interactor.getSteps()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ itemList ->
                adapter.clearAll()
                itemList.forEach { item ->
                    makeAnalyzeAndPushIntoAdapter(item)
                }
            }
    }

    fun loadGoals() {
        disposables + interactor.getGoals()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ itemList ->
                adapter.clearAll()
                itemList.forEach { item ->
                    makeAnalyzeAndPushIntoAdapter(item)
                }
            }
    }

    private fun makeAnalyzeAndPushIntoAdapter(item: Goal) {
        disposables + interactor.setChildrenFor(item)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ mItem ->
                adapter.addElement(interactor.setProgressFor(mItem))
            }
    }

    private fun makeAnalyzeAndPushIntoAdapter(item: Step) {
        disposables + interactor.setChildrenFor(item)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ mItem ->
                adapter.addElement(interactor.setProgressFor(mItem))
            }
    }

    private fun makeAnalyzeAndPushIntoAdapter(item: KeyResult) {
        disposables + interactor.setChildrenFor(item)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ mItem ->
                adapter.addElement(interactor.setProgressFor(mItem))
            }
    }
}