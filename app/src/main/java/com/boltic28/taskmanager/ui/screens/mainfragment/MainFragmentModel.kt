package com.boltic28.taskmanager.ui.screens.mainfragment

import android.util.Log
import com.boltic28.taskmanager.businesslayer.FreeElementsInteractor
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.base.BaseViewModel
import com.boltic28.taskmanager.ui.screens.MainActivity
import com.boltic28.taskmanager.utils.Messenger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainFragmentModel @Inject constructor(
    private val interactor: FreeElementsInteractor,
    val  adapter: ItemAdapter,
    private val  messenger: Messenger,
    override var userManager: UserManager
) : BaseViewModel() {

    val disposables = mutableListOf<Disposable>()

    fun loadTasks(){
        disposables + interactor.getFreeKeys()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result ->
                adapter.refreshData(result)
            },{
                Log.d(MainActivity.TAG, "getKeys - error \n ->$it")
            })
    }

    fun loadKeys(){
        disposables + interactor.getFreeTasks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result ->
                adapter.refreshData(result)
            },{
                Log.d(MainActivity.TAG, "getTasks - error \n ->$it")
            })
    }

    fun loadIdeas(){
        disposables + interactor.getFreeIdeas()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result ->
                adapter.refreshData(result)
            },{
                Log.d(MainActivity.TAG, "getIdeas - error \n ->$it")
            })
    }

    fun loadSteps(){
        disposables + interactor.getFreeSteps()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result ->
                adapter.refreshData(result)
            },{
                Log.d(MainActivity.TAG, "getSteps - error \n ->$it")
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
                Log.d(MainActivity.TAG, "getAll - Goal error \n ->$it")
            })
    }

    private fun makeAnalyzeAndPushIntoAdapter(goal: Goal){
        disposables + interactor.setChildrenFor(goal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {
                adapter.addElement(interactor.setProgressFor(it))
            },{
                Log.d(MainActivity.TAG, "getGoalInfo - Goal error \n ->$it")
            })
    }
}