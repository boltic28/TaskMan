package com.boltic28.taskmanager.ui.screens.goalfragment

import android.util.Log
import androidx.navigation.NavController
import com.boltic28.taskmanager.businesslayer.FreeElementsInteractor
import com.boltic28.taskmanager.businesslayer.GoalFragmentInteractor
import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.HolderController
import com.boltic28.taskmanager.ui.base.BaseEntityFragmentModel
import com.boltic28.taskmanager.ui.screens.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GoalFragmentModel @Inject constructor(
    @AdapterForGoal
    val adapter: ItemAdapter,
    private val goalInteract: GoalFragmentInteractor,
    private val freeElementsInteract: FreeElementsInteractor,
    override var userManager: UserManager
) : BaseEntityFragmentModel<Goal>() {

    override fun refresh() {
        disposables + goalInteract.getGoal(itemId)
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
        disposables + goalInteract.setChildrenFor(goal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mItem.onNext(goalInteract.setProgressFor(it))
            }, {
                Log.d(MainActivity.TAG, "getGoalInfo - Goal error \n ->$it")
            })
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

    private fun addToGoal(item: Step) {
        disposables + goalInteract.updateStep(item.copy(goalId = itemId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isItemsElementIntoRecycler = true
            }, {
                Log.d(MainActivity.TAG, "getStep - error \n ->$it")
            })
    }

    private fun addToGoal(item: KeyResult) {
        disposables + goalInteract.updateKey(item.copy(goalId = itemId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isItemsElementIntoRecycler = true
            }, {
                Log.d(MainActivity.TAG, "getKey - error \n ->$it")
            })
    }

    private fun addToGoal(item: Task) {
        disposables + goalInteract.updateTask(item.copy(goalId = itemId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isItemsElementIntoRecycler = true
            }, {
                Log.d(MainActivity.TAG, "getTask - error \n ->$it")
            })
    }

    private fun addToGoal(item: Idea) {
        disposables + goalInteract.updateIdea(item.copy(goalId = itemId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isItemsElementIntoRecycler = true
            }, {
                Log.d(MainActivity.TAG, "getIdea - error \n ->$it")
            })
    }

    private fun makeFree(item: Idea) {
        disposables + goalInteract.updateIdea(item.copy(goalId = 0L))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isItemsElementIntoRecycler = false
            }, {
                Log.d(MainActivity.TAG, "getIdea - error \n ->$it")
            })
    }

    private fun makeFree(item: Step) {
        disposables + goalInteract.updateStep(item.copy(goalId = 0L))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isItemsElementIntoRecycler = false
            }, {
                Log.d(MainActivity.TAG, "getStep - error \n ->$it")
            })
    }

    private fun makeFree(item: KeyResult) {
        disposables + goalInteract.updateKey(item.copy(goalId = 0L))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isItemsElementIntoRecycler = false
            }, {
                Log.d(MainActivity.TAG, "getKey - error \n ->$it")
            })
    }

    private fun makeFree(item: Task) {
        disposables + goalInteract.updateTask(item.copy(goalId = 0L))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isItemsElementIntoRecycler = false
            }, {
                Log.d(MainActivity.TAG, "getTask - error \n ->$it")
            })
    }

    private fun loadTasks() {
        disposables + freeElementsInteract.getFreeTasks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                adapter.addList(result)
            }, {
                Log.d(MainActivity.TAG, "getTasks - error \n ->$it")
            })
    }

    private fun loadKeys() {
        disposables + freeElementsInteract.getFreeKeys()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                adapter.addList(result)
            }, {
                Log.d(MainActivity.TAG, "getKeys - error \n ->$it")
            })
    }

    private fun loadIdeas() {
        disposables + freeElementsInteract.getFreeIdeas()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                adapter.addList(result)
            }, {
                Log.d(MainActivity.TAG, "getIdeas - error \n ->$it")
            })
    }

    private fun loadSteps() {
        disposables + freeElementsInteract.getFreeSteps()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                adapter.addList(result)
            }, {
                Log.d(MainActivity.TAG, "getSteps - error \n ->$it")
            })
    }
}