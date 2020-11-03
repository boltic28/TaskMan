package com.boltic28.taskmanager.ui.screens.stepfragment

import android.util.Log
import androidx.navigation.NavController
import com.boltic28.taskmanager.businesslayer.StepFragmentInteractor
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.HolderController
import com.boltic28.taskmanager.ui.base.BaseEntityFragmentModel
import com.boltic28.taskmanager.ui.screens.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

class StepFragmentModel @Inject constructor(
    @AdapterForStep
    val adapter: ItemAdapter,
    val interactor: StepFragmentInteractor,
    override var userManager: UserManager,
): BaseEntityFragmentModel<Step>() {

    override fun refresh() {
        disposables + interactor.getStepById(itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    initStepValue(it)
                }, {
                    Log.d(MainActivity.TAG, "getGoal - error \n ->$it")
                }
            )
    }

    private fun initStepValue(step: Step) {
        disposables + interactor.setChildrenFor(step)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mItem.onNext(interactor.setProgressFor(it))
            }, {
                Log.d(MainActivity.TAG, "getGoalInfo - Goal error \n ->$it")
            })
    }

    fun loadFreeElementIntoAdapter(nav: NavController) {
        adapter.clearAll()
        adapter.setAdapterListener(object : HolderController.OnActionClickListener {
            override fun onActionButtonClick(item: Any) {
                if (item is Idea) addToStep(item)
                if (item is Task) addToStep(item)
            }
            override fun onViewClick(item: Any) {
                goToItemFragment(item, nav)
            }
        })
        loadTasks()
        loadIdeas()
    }

    fun loadGoalsIntoAdapter(step: Step, nav: NavController){
        adapter.clearAll()
        adapter.setAdapterListener(object : HolderController.OnActionClickListener {
            override fun onActionButtonClick(item: Any) {
                item as Goal
                disposables + interactor.update(step.copy(goalId = item.id))
                    .subscribeOn(Schedulers.io())
                    .subscribe { _ ->
                        refresh()
                        isItemsElementIntoRecycler = false
                    }
            }
            override fun onViewClick(item: Any) {
                goToItemFragment(item, nav)
            }
        })
        loadGoals()
    }

    fun loadStepsElementIntoAdapter(step: Step, nav: NavController) {
        adapter.clearAll()
        adapter.setAdapterListener(object : HolderController.OnActionClickListener {
            override fun onActionButtonClick(item: Any) {
                if (item is Task) makeFree(item)
                if (item is Idea) makeFree(item)
            }
            override fun onViewClick(item: Any) {
                goToItemFragment(item, nav)
            }
        })
        adapter.addList(step.tasks)
        adapter.addList(step.ideas)

    }

    private fun addToStep(item: Task) {
        disposables + interactor.updateTask(item.copy(stepId = itemId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isItemsElementIntoRecycler = true
            }, {
                Log.d(MainActivity.TAG, "getTask - error \n ->$it")
            })
    }

    private fun addToStep(item: Idea) {
        disposables + interactor.updateIdea(item.copy(stepId = itemId))
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
        disposables + interactor.updateIdea(item.copy(stepId = 0L))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isItemsElementIntoRecycler = false
            }, {
                Log.d(MainActivity.TAG, "getIdea - error \n ->$it")
            })
    }

    private fun makeFree(item: Task) {
        disposables + interactor.updateTask(item.copy(stepId = 0L))
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
        disposables + interactor.getFreeTasks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                adapter.addList(result)
            }, {
                Log.d(MainActivity.TAG, "getTasks - error \n ->$it")
            })
    }

    private fun loadIdeas() {
        disposables + interactor.getFreeIdeas()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                adapter.addList(result)
            }, {
                Log.d(MainActivity.TAG, "getIdeas - error \n ->$it")
            })
    }

    private fun loadGoals(){
        disposables + interactor.getGoals()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                adapter.addList(result)
            }, {
                Log.d(MainActivity.TAG, "getIdeas - error \n ->$it")
            })
    }
}