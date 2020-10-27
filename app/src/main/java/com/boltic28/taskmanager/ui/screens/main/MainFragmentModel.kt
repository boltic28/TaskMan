package com.boltic28.taskmanager.ui.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.businesslayer.FreeElementsInteractor
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.di.AppDagger
import com.boltic28.taskmanager.ui.adapter.controllers.HolderController
import com.boltic28.taskmanager.ui.screens.MainActivity
import com.boltic28.taskmanager.utils.Messenger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainFragmentModel : ViewModel() {

    @Inject
    lateinit var interactor: FreeElementsInteractor

    @Inject
    lateinit var adapter: ItemAdapter

    @Inject
    lateinit var messenger: Messenger

    val disposables = mutableListOf<Disposable>()

    init {
        AppDagger.mainComponent.inject(this)
    }

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