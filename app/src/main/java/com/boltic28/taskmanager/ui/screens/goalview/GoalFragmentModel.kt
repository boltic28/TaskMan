package com.boltic28.taskmanager.ui.screens.goalview

import android.util.Log
import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.businesslayer.GoalFragmentInteractor
import com.boltic28.taskmanager.businesslayer.FreeElementsInteractor
import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.di.AppDagger
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.screens.MainActivity
import com.boltic28.taskmanager.utils.Messenger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GoalFragmentModel: ViewModel() {

    @Inject
    lateinit var goalInteractor: GoalFragmentInteractor

    @Inject
    lateinit var freeElementsInteractor: FreeElementsInteractor

    @Inject
    lateinit var messenger: Messenger

    @Inject
    lateinit var adapter: ItemAdapter

    lateinit var goal: Goal

    val disposables = mutableListOf<Disposable>()

    init {
        AppDagger.component.injectModel(this)
    }

    fun addToGoal(item: Step){
        disposables + goalInteractor.addStep(item)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refreshGoal(goal.id)
            },{
                Log.d(MainActivity.TAG, "getStep - error \n ->$it")
            })
    }

    fun addToGoal(item: KeyResult){
        disposables + goalInteractor.addKey(item)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refreshGoal(goal.id)
            },{
                Log.d(MainActivity.TAG, "getKey - error \n ->$it")
            })
    }

    fun addToGoal(item: Task){
        disposables + goalInteractor.addTask(item)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refreshGoal(goal.id)
            },{
                Log.d(MainActivity.TAG, "getTask - error \n ->$it")
            })
    }

    fun addToGoal(item: Idea){
        disposables + goalInteractor.addIdea(item)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refreshGoal(goal.id)
            },{
                Log.d(MainActivity.TAG, "getIdea - error \n ->$it")
            })
    }

    fun loadTasks(){
        disposables + freeElementsInteractor.getFreeKeys()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result ->
                adapter.refreshData(result)
            },{
                Log.d(MainActivity.TAG, "getTasks - error \n ->$it")
            })
    }

    fun loadKeys(){
        disposables + freeElementsInteractor.getFreeTasks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result ->
                adapter.refreshData(result)
            },{
                Log.d(MainActivity.TAG, "getKeys - error \n ->$it")
            })
    }

    fun loadIdeas(){
        disposables + freeElementsInteractor.getFreeIdeas()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result ->
                adapter.refreshData(result)
            },{
                Log.d(MainActivity.TAG, "getIdeas - error \n ->$it")
            })
    }

    fun loadSteps(){
        disposables + freeElementsInteractor.getFreeSteps()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result ->
                adapter.refreshData(result)
            },{
                Log.d(MainActivity.TAG, "getSteps - error \n ->$it")
            })
    }

    fun refreshGoal(id: Long){
        disposables + goalInteractor.getGoal(id)
            .subscribe(
                {
                    makeAnalyzeAndPushIntoAdapter(it)
                },{
                    Log.d(MainActivity.TAG, "getGoal - error \n ->$it")
                }
            )
    }

    private fun makeAnalyzeAndPushIntoAdapter(goal: Goal){
        disposables + goalInteractor.setChildrenFor(goal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {
                this.goal = goalInteractor.setProgressFor(it)
            },{
                Log.d(MainActivity.TAG, "getGoalInfo - Goal error \n ->$it")
            })
    }

}