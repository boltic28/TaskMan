package com.boltic28.taskmanager.ui.screens.keyfragment

import androidx.navigation.NavController
import com.boltic28.taskmanager.businesslayer.KeyFragmentInteractor
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.HolderController
import com.boltic28.taskmanager.ui.base.BaseEntityFragmentModel
import com.boltic28.taskmanager.utils.Messenger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class KeyFragmentModel @Inject constructor(
    @AdapterForKey
    val adapter: ItemAdapter,
    val interactor: KeyFragmentInteractor,
    override var userManager: UserManager,
    val messenger: Messenger
) : BaseEntityFragmentModel<KeyResult>() {
    override fun refresh() {
        disposables + interactor.getKeyById(itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    initValue(it)
                }, {
                }
            )
    }

    private fun initValue(key: KeyResult) {
        disposables + interactor.setChildrenFor(key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mItem.onNext(interactor.setProgressFor(it))
                }, {
                }
            )
    }


    fun loadGoalsIntoAdapter(key: KeyResult, nav: NavController) {
        adapter.clearAll()
        adapter.setAdapterListener(object : HolderController.OnActionClickListener {
            override fun onActionButtonClick(item: Any) {
                item as Goal
                disposables + interactor.update(key.copy(goalId = item.id))
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

    fun loadKeysElementIntoAdapter(key: KeyResult, nav: NavController) {
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
        adapter.addList(key.tasks)
        adapter.addList(key.ideas)

    }

    fun loadFreeElementIntoAdapter(nav: NavController) {
        adapter.clearAll()
        adapter.setAdapterListener(object : HolderController.OnActionClickListener {
            override fun onActionButtonClick(item: Any) {
                if (item is Idea) addToKey(item)
                if (item is Task) addToKey(item)
            }

            override fun onViewClick(item: Any) {
                goToItemFragment(item, nav)
            }
        })
        loadTasks()
        loadIdeas()
    }

    private fun addToKey(item: Task) {
        disposables + interactor.updateTask(item.copy(keyId = itemId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isItemsElementIntoRecycler = true
            }, {
            })
    }

    private fun addToKey(item: Idea) {
        disposables + interactor.updateIdea(item.copy(keyId = itemId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isItemsElementIntoRecycler = true
            }, {
            })
    }

    private fun makeFree(item: Idea) {
        disposables + interactor.updateIdea(item.copy(keyId = 0L))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isItemsElementIntoRecycler = false
            }, {
            })
    }

    private fun makeFree(item: Task) {
        disposables + interactor.updateTask(item.copy(keyId = 0L))
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