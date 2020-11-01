package com.boltic28.taskmanager.ui.screens.goalfragment

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.businesslayer.GoalFragmentInteractor
import com.boltic28.taskmanager.businesslayer.FreeElementsInteractor
import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.di.App
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.HolderController
import com.boltic28.taskmanager.ui.screens.MainActivity
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment.Companion.IDEA_ID
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment.Companion.KEY_ID
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment.Companion.STEP_ID
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment.Companion.TASK_ID
import com.boltic28.taskmanager.utils.Messenger
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class GoalFragmentModel : ViewModel() {

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
    var isGoalsElementIntoRecycler = false

    val disposables = mutableListOf<Disposable>()

    init {
        App.goalComponent.inject(this)
    }

    fun loadFreeElementIntoAdapter(nav: NavController) {
        adapter.clearAll()
        adapter.setAdapterListener(object : HolderController.OnActionClickListener {
            override fun onActionButtonClick(item: Any) {
                if (item is Step) addToGoal(item)
                if (item is Task) addToGoal(item)
                if (item is KeyResult) addToGoal(item)
                if (item is Idea) addToGoal(item)
            }
            override fun onViewClick(item: Any) {
                goToItemFragment(item, nav)
            }

        })
        loadKeys()
        loadSteps()
        loadTasks()
        loadIdeas()
    }

    fun loadGoalsElementIntoAdapter(goal: Goal, nav: NavController) {
        adapter.clearAll()
        adapter.setAdapterListener(object : HolderController.OnActionClickListener {
            override fun onActionButtonClick(item: Any) {
                if (item is Step) makeFree(item)
                if (item is Task) makeFree(item)
                if (item is KeyResult) makeFree(item)
                if (item is Idea) makeFree(item)
            }
            override fun onViewClick(item: Any) {
                goToItemFragment(item, nav)
            }
        })
        adapter.addList(goal.keys)
        adapter.addList(goal.steps)
        adapter.addList(goal.tasks)
        adapter.addList(goal.ideas)
    }

    fun refreshGoal() {
        disposables + goalInteractor.getGoal(goalId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    initGoalValue(it)
                }, {
                    Log.d(MainActivity.TAG, "getGoal - error \n ->$it")
                }
            )
    }

    private fun initGoalValue(goal: Goal) {
        disposables + goalInteractor.setChildrenFor(goal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mGoal.onNext(goalInteractor.setProgressFor(it))
            }, {
                Log.d(MainActivity.TAG, "getGoalInfo - Goal error \n ->$it")
            })
    }

    private fun addToGoal(item: Step) {
        disposables + goalInteractor.updateStep(item.copy(goalId = goalId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refreshGoal()
                isGoalsElementIntoRecycler = true
            }, {
                Log.d(MainActivity.TAG, "getStep - error \n ->$it")
            })
    }

    private fun addToGoal(item: KeyResult) {
        disposables + goalInteractor.updateKey(item.copy(goalId = goalId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refreshGoal()
                isGoalsElementIntoRecycler = true
            }, {
                Log.d(MainActivity.TAG, "getKey - error \n ->$it")
            })
    }

    private fun addToGoal(item: Task) {
        disposables + goalInteractor.updateTask(item.copy(goalId = goalId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refreshGoal()
                isGoalsElementIntoRecycler = true
            }, {
                Log.d(MainActivity.TAG, "getTask - error \n ->$it")
            })
    }

    private fun addToGoal(item: Idea) {
        disposables + goalInteractor.updateIdea(item.copy(goalId = goalId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refreshGoal()
                isGoalsElementIntoRecycler = true
            }, {
                Log.d(MainActivity.TAG, "getIdea - error \n ->$it")
            })
    }

    private fun makeFree(item: Idea) {
        disposables + goalInteractor.updateIdea(item.copy(goalId = 0L))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refreshGoal()
                isGoalsElementIntoRecycler = false
            }, {
                Log.d(MainActivity.TAG, "getIdea - error \n ->$it")
            })
    }

    private fun makeFree(item: Step) {
        disposables + goalInteractor.updateStep(item.copy(goalId = 0L))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refreshGoal()
                isGoalsElementIntoRecycler = false
            }, {
                Log.d(MainActivity.TAG, "getStep - error \n ->$it")
            })
    }

    private fun makeFree(item: KeyResult) {
        disposables + goalInteractor.updateKey(item.copy(goalId = 0L))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refreshGoal()
                isGoalsElementIntoRecycler = false
            }, {
                Log.d(MainActivity.TAG, "getKey - error \n ->$it")
            })
    }

    private fun makeFree(item: Task) {
        disposables + goalInteractor.updateTask(item.copy(goalId = 0L))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refreshGoal()
                isGoalsElementIntoRecycler = false
            }, {
                Log.d(MainActivity.TAG, "getTask - error \n ->$it")
            })
    }

    private fun loadTasks() {
        disposables + freeElementsInteractor.getFreeTasks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                adapter.addList(result)
            }, {
                Log.d(MainActivity.TAG, "getTasks - error \n ->$it")
            })
    }

    private fun loadKeys() {
        disposables + freeElementsInteractor.getFreeKeys()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                adapter.addList(result)
            }, {
                Log.d(MainActivity.TAG, "getKeys - error \n ->$it")
            })
    }

    private fun loadIdeas() {
        disposables + freeElementsInteractor.getFreeIdeas()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                adapter.addList(result)
            }, {
                Log.d(MainActivity.TAG, "getIdeas - error \n ->$it")
            })
    }

    private fun loadSteps() {
        disposables + freeElementsInteractor.getFreeSteps()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                adapter.addList(result)
            }, {
                Log.d(MainActivity.TAG, "getSteps - error \n ->$it")
            })
    }

    private fun goToItemFragment(item: Any, nav: NavController){
        val bundle = Bundle()
        if (item is Step){
            bundle.putLong(STEP_ID, item.id)
            nav.navigate(R.id.stepFragment, bundle)
        }
        if (item is Task){
            bundle.putLong(TASK_ID, item.id)
            nav.navigate(R.id.taskFragment, bundle)
        }
        if (item is Idea){
            bundle.putLong(IDEA_ID, item.id)
            nav.navigate(R.id.ideaFragment, bundle)
        }
        if (item is KeyResult){
            bundle.putLong(KEY_ID, item.id)
            nav.navigate(R.id.keyFragment, bundle)
        }
    }
}