package com.boltic28.taskmanager.ui.screens.goalfragment

import androidx.navigation.NavController
import com.boltic28.taskmanager.businesslayer.FreeElementsInteractor
import com.boltic28.taskmanager.businesslayer.GoalFragmentInteractor
import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.HolderController
import com.boltic28.taskmanager.ui.base.BaseEntityFragmentModel
import com.boltic28.taskmanager.ui.constant.GOAL_EXTRA
import com.boltic28.taskmanager.ui.constant.NO_ID
import com.boltic28.taskmanager.utils.Messenger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GoalFragmentModel @Inject constructor(
    @AdapterForGoal
    val adapter: ItemAdapter,
    private val interactor: GoalFragmentInteractor,
    private val freeElementsInteract: FreeElementsInteractor,
    override var userManager: UserManager,
    val messenger: Messenger
) : BaseEntityFragmentModel<Goal>() {

    override val extraKey: String = GOAL_EXTRA

    override fun refresh() {
        disposables + interactor.getGoal(itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    initGoalValue(it)
                }, {
                }
            )
    }

    private fun initGoalValue(goal: Goal) {
        disposables + interactor.setChildrenFor(goal)
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
            override fun isNeedToShowConnection(): Boolean = true
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
        disposables + interactor.updateStep(item.copy(goalId = itemId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isItemsElementIntoRecycler = true
            }, {
            })
    }

    private fun addToGoal(item: KeyResult) {
        disposables + interactor.updateKey(item.copy(goalId = itemId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isItemsElementIntoRecycler = true
            }, {
            })
    }

    private fun addToGoal(item: Task) {
        disposables + interactor.updateTask(item.copy(goalId = itemId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isItemsElementIntoRecycler = true
            }, {
            })
    }

    private fun addToGoal(item: Idea) {
        disposables + interactor.updateIdea(item.copy(goalId = itemId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isItemsElementIntoRecycler = true
            }, {
            })
    }

    private fun makeFree(item: Idea) {
        disposables + interactor.updateIdea(item.copy(goalId = NO_ID))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isItemsElementIntoRecycler = false
            }, {
            })
    }

    private fun makeFree(item: Step) {
        disposables + interactor.updateStep(item.copy(goalId = NO_ID))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isItemsElementIntoRecycler = false
            }, {
            })
    }

    private fun makeFree(item: KeyResult) {
        disposables + interactor.updateKey(item.copy(goalId = NO_ID))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isItemsElementIntoRecycler = false
            }, {
            })
    }

    private fun makeFree(item: Task) {
        disposables + interactor.updateTask(item.copy(goalId = NO_ID))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isItemsElementIntoRecycler = false
            }, {
            })
    }

    private fun loadTasks() {
        disposables + freeElementsInteract.getFreeTasks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                adapter.addList(result)
            }, {
            })
    }

    private fun loadKeys() {
        disposables + freeElementsInteract.getFreeKeys()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                adapter.addList(result)
            }, {
            })
    }

    private fun loadIdeas() {
        disposables + freeElementsInteract.getFreeIdeas()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                adapter.addList(result)
            }, {
            })
    }

    private fun loadSteps() {
        disposables + freeElementsInteract.getFreeSteps()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                adapter.addList(result)
            }, {
            })
    }
}