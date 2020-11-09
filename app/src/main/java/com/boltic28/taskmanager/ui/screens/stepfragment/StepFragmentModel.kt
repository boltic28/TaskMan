package com.boltic28.taskmanager.ui.screens.stepfragment

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
import com.boltic28.taskmanager.utils.Messenger
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class StepFragmentModel @Inject constructor(
    @AdapterForStep
    val adapter: ItemAdapter,
    private val interactor: StepFragmentInteractor,
    override var userManager: UserManager,
    val messenger: Messenger
) : BaseEntityFragmentModel<Step>() {

    override fun refresh() {
        disposables + interactor.getStepById(itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    initStepValue(it)
                }, {
                }
            )
    }

    fun update(item: Step){
        disposables + interactor.update(item)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _ ->
                refresh()
                isItemsElementIntoRecycler = false
            }
    }

    fun getParentName(item: Step): Single<String> =
        interactor.getParentName(item.goalId)

    private fun initStepValue(step: Step) {
        disposables + interactor.setChildrenFor(step)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mItem.onNext(interactor.setProgressFor(it))
            }, {
            })
    }

    fun loadFreeElementIntoAdapter(nav: NavController) {
        adapter.clearAll()
        adapter.setAdapterListener(object : HolderController.OnActionClickListener {
            override fun isNeedToShowConnection(): Boolean = true
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

    fun loadGoalsIntoAdapter(step: Step, nav: NavController) {
        adapter.clearAll()
        adapter.setAdapterListener(object : HolderController.OnActionClickListener {
            override fun isNeedToShowConnection(): Boolean = true
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
            override fun isNeedToShowConnection(): Boolean = true
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
            })
    }

    private fun loadTasks() {
        disposables + interactor.getFreeTasks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                adapter.addList(result)
            }, {
            })
    }

    private fun loadIdeas() {
        disposables + interactor.getFreeIdeas()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                adapter.addList(result)
            }, {
            })
    }

    private fun loadGoals() {
        disposables + interactor.getGoals()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                adapter.addList(result)
            }, {
            })
    }
}