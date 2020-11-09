package com.boltic28.taskmanager.ui.screens.ideafragment

import androidx.navigation.NavController
import com.boltic28.taskmanager.businesslayer.IdeaFragmentInteractor
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.HolderController
import com.boltic28.taskmanager.ui.base.BaseEntityFragmentModel
import com.boltic28.taskmanager.utils.Messenger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class IdeaFragmentModel @Inject constructor(
    @AdapterForIdea
    val adapter: ItemAdapter,
    val interactor: IdeaFragmentInteractor,
    override var userManager: UserManager,
    val messenger: Messenger
) : BaseEntityFragmentModel<Idea>() {

    override fun refresh() {
        disposables + interactor.getIdeaById(itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { item ->
                mItem.onNext(item)
            }
    }

    fun update(idea: Idea) {
        disposables + interactor.update(idea)
            .subscribeOn(Schedulers.io())
            .subscribe { _ -> refresh() }
    }

    fun loadFreeElementsIntoAdapter(idea: Idea, nav: NavController) {
        adapter.setAdapterListener(object : HolderController.OnActionClickListener {
            override fun isNeedToShowConnection(): Boolean = false
            override fun onActionButtonClick(item: Any) {
                attachTo(idea, item)
            }
            override fun onViewClick(item: Any) {
                goToItemFragment(item, nav)
            }
        })
        disposables + interactor.getStepsGoalsKeys()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                adapter.refreshData(list)
            }
    }

    private fun attachTo(idea: Idea, item: Any) {
        if (item is Step) {
            disposables + interactor.update(idea.copy(stepId = item.id))
                .subscribeOn(Schedulers.io())
                .subscribe { _ ->
                    refresh() }
        }
        if (item is Goal) {
            disposables + interactor.update(idea.copy(goalId = item.id))
                .subscribeOn(Schedulers.io())
                .subscribe { _ -> refresh() }
        }
        if (item is KeyResult) {
            disposables + interactor.update(idea.copy(keyId = item.id))
                .subscribeOn(Schedulers.io())
                .subscribe { _ -> refresh() }
        }
    }
}