package com.boltic28.taskmanager.ui.screens.goalview

import android.util.Log
import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.businesslayer.GoalFragmentInteractor
import com.boltic28.taskmanager.businesslayer.FreeElementsInteractor
import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.di.AppDagger
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.screens.MainActivity
import com.boltic28.taskmanager.utils.Messenger
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
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

    lateinit var userManager: UserManager

    private val mGoal = BehaviorSubject.create<Goal>()
    val goal: Observable<Goal>
    get() = mGoal.hide()

    var goalId = 0L

    val disposables = mutableListOf<Disposable>()

    init {
        AppDagger.goalComponent.inject(this)
    }

    fun loadFreeElementIntoAdapter(){
        adapter.clearAll()
        loadKeys()
        loadSteps()
        loadTasks()
        loadIdeas()
    }

    fun loadGoalsElementIntoAdapter(goal: Goal){
        adapter.clearAll()
        adapter.addList(goal.keys)
        adapter.addList(goal.steps)
        adapter.addList(goal.tasks)
        adapter.addList(goal.ideas)
    }

    fun addToGoal(item: Step){
        disposables + goalInteractor.addStep(item)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refreshGoal()
            },{
                Log.d(MainActivity.TAG, "getStep - error \n ->$it")
            })
    }

    fun addToGoal(item: KeyResult){
        disposables + goalInteractor.addKey(item)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refreshGoal()
            },{
                Log.d(MainActivity.TAG, "getKey - error \n ->$it")
            })
    }

    fun addToGoal(item: Task){
        disposables + goalInteractor.addTask(item)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refreshGoal()
            },{
                Log.d(MainActivity.TAG, "getTask - error \n ->$it")
            })
    }

    fun addToGoal(item: Idea){
        disposables + goalInteractor.addIdea(item)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refreshGoal()
            },{
                Log.d(MainActivity.TAG, "getIdea - error \n ->$it")
            })
    }

    private fun loadTasks(){
        disposables + freeElementsInteractor.getFreeKeys()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result ->
                adapter.refreshData(result)
            },{
                Log.d(MainActivity.TAG, "getTasks - error \n ->$it")
            })
    }

    private fun loadKeys(){
        disposables + freeElementsInteractor.getFreeTasks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result ->
                adapter.refreshData(result)
            },{
                Log.d(MainActivity.TAG, "getKeys - error \n ->$it")
            })
    }

    private fun loadIdeas(){
        disposables + freeElementsInteractor.getFreeIdeas()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result ->
                adapter.refreshData(result)
            },{
                Log.d(MainActivity.TAG, "getIdeas - error \n ->$it")
            })
    }

    private fun loadSteps(){
        disposables + freeElementsInteractor.getFreeSteps()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result ->
                adapter.refreshData(result)
            },{
                Log.d(MainActivity.TAG, "getSteps - error \n ->$it")
            })
    }

    fun refreshGoal(){
        disposables + goalInteractor.getGoal(goalId)
            .subscribe(
                {
                    initGoalValue(it)
                },{
                    Log.d(MainActivity.TAG, "getGoal - error \n ->$it")
                }
            )
    }

    private fun initGoalValue(goal: Goal){
        disposables + goalInteractor.setChildrenFor(goal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {
                mGoal.onNext(goalInteractor.setProgressFor(it))
            },{
                Log.d(MainActivity.TAG, "getGoalInfo - Goal error \n ->$it")
            })
    }

}