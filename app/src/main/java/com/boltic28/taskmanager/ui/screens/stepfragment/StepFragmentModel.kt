package com.boltic28.taskmanager.ui.screens.stepfragment

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.businesslayer.StepFragmentInteractor
import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.di.AppDagger
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.HolderController
import com.boltic28.taskmanager.ui.screens.MainActivity
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment
import com.boltic28.taskmanager.utils.Messenger
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class StepFragmentModel: ViewModel() {

    @Inject
    lateinit var interactor: StepFragmentInteractor

    @Inject
    lateinit var messenger: Messenger

    @Inject
    lateinit var adapter: ItemAdapter

    lateinit var userManager: UserManager

    private val mStep = BehaviorSubject.create<Step>()
    val step: Observable<Step>
        get() = mStep.hide()

    var stepId = 0L
    var isStepsElementIntoRecycler = false

    val disposables = mutableListOf<Disposable>()

    init {
        AppDagger.stepComponent.inject(this)
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
                        refreshStep()
                        isStepsElementIntoRecycler = false
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

    fun refreshStep() {
        disposables + interactor.getStepById(stepId)
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
                mStep.onNext(interactor.setProgressFor(it))
            }, {
                Log.d(MainActivity.TAG, "getGoalInfo - Goal error \n ->$it")
            })
    }

    private fun addToStep(item: Task) {
        disposables + interactor.updateTask(item.copy(stepId = stepId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refreshStep()
                isStepsElementIntoRecycler = true
            }, {
                Log.d(MainActivity.TAG, "getTask - error \n ->$it")
            })
    }

    private fun addToStep(item: Idea) {
        disposables + interactor.updateIdea(item.copy(stepId = stepId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refreshStep()
                isStepsElementIntoRecycler = true
            }, {
                Log.d(MainActivity.TAG, "getIdea - error \n ->$it")
            })
    }

    private fun makeFree(item: Idea) {
        disposables + interactor.updateIdea(item.copy(stepId = 0L))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refreshStep()
                isStepsElementIntoRecycler = false
            }, {
                Log.d(MainActivity.TAG, "getIdea - error \n ->$it")
            })
    }

    private fun makeFree(item: Task) {
        disposables + interactor.updateTask(item.copy(stepId = 0L))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refreshStep()
                isStepsElementIntoRecycler = false
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

    private fun goToItemFragment(item: Any, nav: NavController){
        val bundle = Bundle()
        if (item is Task){
            bundle.putLong(MainFragment.TASK_ID, item.id)
            nav.navigate(R.id.taskFragment, bundle)
        }
        if (item is Idea){
            bundle.putLong(MainFragment.IDEA_ID, item.id)
            nav.navigate(R.id.ideaFragment, bundle)
        }
        if (item is KeyResult){
            bundle.putLong(MainFragment.KEY_ID, item.id)
            nav.navigate(R.id.keyFragment, bundle)
        }
    }
}