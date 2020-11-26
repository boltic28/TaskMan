package com.boltic28.taskmanager.ui.screens.keyfragment

import android.os.Bundle
import androidx.navigation.NavController
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.businesslayer.interactors.KeyFragmentInteractor
import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.HolderController
import com.boltic28.taskmanager.ui.base.BaseEntityFragmentModel
import com.boltic28.taskmanager.ui.constant.KEY_EXTRA
import com.boltic28.taskmanager.ui.constant.LOAD_LIST
import com.boltic28.taskmanager.ui.constant.NO_ID
import com.boltic28.taskmanager.utils.Messenger
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class KeyFragmentModel @Inject constructor(
    @AdapterForKey
    val adapter: ItemAdapter,
    private val interactor: KeyFragmentInteractor,
    override var userManager: UserManager,
    val messenger: Messenger
) : BaseEntityFragmentModel<KeyResult>() {

    override val extraKey: String = KEY_EXTRA

    override fun refresh() {
        disposables + interactor.getItemById(itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    initValue(it)
                }, {
                }
            )
    }

    override fun update(item: BaseItem) {
        disposables + interactor.update(item as KeyResult)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _ ->
                refresh()
                isLoadFreeElements = false
            }
    }

    override fun delete(item: BaseItem, nav: NavController){
        disposables + interactor.delete(item as KeyResult)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ deleted ->
                val bundle = Bundle()
                bundle.putString(LOAD_LIST, KEY_EXTRA)
                nav.navigate(R.id.mainFragment, bundle)
                messenger.showMessage("$deleted item(s) deleted")
            }
    }

    fun getParentName(item: KeyResult): Single<String> =
        interactor.getParentName(item)

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
            override fun isNeedToShowConnection(): Boolean = true
            override fun onActionButtonClick(item: BaseItem) {
                item as Goal
                disposables + interactor.update(key.copy(goalId = item.id))
                    .subscribeOn(Schedulers.io())
                    .subscribe { _ ->
                        refresh()
                        isLoadFreeElements = false
                    }
            }
            override fun onViewClick(item: BaseItem) {
                goToItemFragment(item, nav)
            }
        })
        loadGoals()
    }

    fun loadKeysElementIntoAdapter(key: KeyResult, nav: NavController) {
        adapter.clearAll()
        adapter.setAdapterListener(object : HolderController.OnActionClickListener {
            override fun isNeedToShowConnection(): Boolean = true
            override fun onActionButtonClick(item: BaseItem) {
                if (item is Task) makeFree(item)
                if (item is Idea) makeFree(item)
            }

            override fun onViewClick(item: BaseItem) {
                goToItemFragment(item, nav)
            }
        })
        adapter.addList(key.tasks)
        adapter.addList(key.ideas)

    }

    fun loadFreeElementIntoAdapter(nav: NavController) {
        adapter.clearAll()
        adapter.setAdapterListener(object : HolderController.OnActionClickListener {
            override fun isNeedToShowConnection(): Boolean = true
            override fun onActionButtonClick(item: BaseItem) {
                if (item is Idea) addToKey(item)
                if (item is Task) addToKey(item)
            }

            override fun onViewClick(item: BaseItem) {
                goToItemFragment(item, nav)
            }
        })
        loadTasks()
        loadIdeas()
    }

    private fun addToKey(item: Task) {
        disposables + interactor.update(item.copy(keyId = itemId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isLoadFreeElements = true
            }, {
            })
    }

    private fun addToKey(item: Idea) {
        disposables + interactor.update(item.copy(keyId = itemId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isLoadFreeElements = true
            }, {
            })
    }

    private fun makeFree(item: Idea) {
        disposables + interactor.update(item.copy(keyId = NO_ID))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
                isLoadFreeElements = false
            }, {
            })
    }

    private fun makeFree(item: Task) {
        disposables + interactor.update(item.copy(keyId = NO_ID))
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
        disposables + interactor.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                adapter.addList(result)
            }, {
            })
    }
}