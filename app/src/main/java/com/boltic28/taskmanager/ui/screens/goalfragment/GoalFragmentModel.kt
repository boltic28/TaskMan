package com.boltic28.taskmanager.ui.screens.goalfragment

import android.os.Bundle
import androidx.navigation.NavController
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.businesslayer.interactors.GoalFragmentInteractor
import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.HolderController
import com.boltic28.taskmanager.ui.base.BaseEntityFragmentModel
import com.boltic28.taskmanager.ui.constant.GOAL_EXTRA
import com.boltic28.taskmanager.ui.constant.LOAD_LIST
import com.boltic28.taskmanager.ui.constant.NO_ID
import com.boltic28.taskmanager.utils.Messenger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GoalFragmentModel @Inject constructor(
    @AdapterForGoal
    val adapter: ItemAdapter,
    private val interactor: GoalFragmentInteractor,
    override var userManager: UserManager,
    val messenger: Messenger
) : BaseEntityFragmentModel<Goal>() {

    override val extraKey: String = GOAL_EXTRA

    override fun refresh() {
        disposables + interactor.getItemById(itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { goal -> initGoalValue(goal) }
    }

    override fun update(item: BaseItem){
        disposables + interactor.update(item as Goal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ _ -> refresh() }
    }

    override fun delete(item: BaseItem, nav: NavController){
        disposables + interactor.delete(item as Goal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ deleted ->
                val bundle = Bundle()
                bundle.putString(LOAD_LIST, GOAL_EXTRA)
                nav.navigate(R.id.mainFragment, bundle)
                messenger.showMessage("$deleted item(s) deleted")
            }
    }

    private fun initGoalValue(goal: Goal) {
        disposables + interactor.setChildrenFor(goal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ mGoal ->
                mItem.onNext(interactor.setProgressFor(mGoal))
            }
    }

    fun loadFreeElementIntoAdapter(nav: NavController) {
        adapter.clearAll()
        adapter.setAdapterListener(object : HolderController.OnActionClickListener {
            override fun isNeedToShowConnection(): Boolean = true
            override fun onActionButtonClick(item: BaseItem) {
                if (item is Step) addToGoal(item)
                if (item is Task) addToGoal(item)
                if (item is KeyResult) addToGoal(item)
                if (item is Idea) addToGoal(item)
            }
            override fun onViewClick(item: BaseItem) {
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
            override fun onActionButtonClick(item: BaseItem) {
                if (item is Step) makeFree(item)
                if (item is Task) makeFree(item)
                if (item is KeyResult) makeFree(item)
                if (item is Idea) makeFree(item)
            }
            override fun onViewClick(item: BaseItem) {
                goToItemFragment(item, nav)
            }
        })
        adapter.addList(goal.keys)
        adapter.addList(goal.steps)
        adapter.addList(goal.tasks)
        adapter.addList(goal.ideas)
    }

    private fun addToGoal(item: Step) {
        disposables + interactor.update(item.copy(goalId = itemId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isLoadFreeElements = true
            }, {
            })
    }

    private fun addToGoal(item: KeyResult) {
        disposables + interactor.update(item.copy(goalId = itemId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isLoadFreeElements = true
            }, {
            })
    }

    private fun addToGoal(item: Task) {
        disposables + interactor.update(item.copy(goalId = itemId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isLoadFreeElements = true
            }, {
            })
    }

    private fun addToGoal(item: Idea) {
        disposables + interactor.update(item.copy(goalId = itemId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isLoadFreeElements = true
            }, {
            })
    }

    private fun makeFree(item: Idea) {
        disposables + interactor.update(item.copy(goalId = NO_ID))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isLoadFreeElements = false
            }, {
            })
    }

    private fun makeFree(item: Step) {
        disposables + interactor.update(item.copy(goalId = NO_ID))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isLoadFreeElements = false
            }, {
            })
    }

    private fun makeFree(item: KeyResult) {
        disposables + interactor.update(item.copy(goalId = NO_ID))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isLoadFreeElements = false
            }, {
            })
    }

    private fun makeFree(item: Task) {
        disposables + interactor.update(item.copy(goalId = NO_ID))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isLoadFreeElements = false
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

    private fun loadKeys() {
        disposables + interactor.getFreeKeys()
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

    private fun loadSteps() {
        disposables + interactor.getFreeSteps()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                adapter.addList(result)
            }, {
            })
    }
}