package com.boltic28.taskmanager.ui.screens.ideafragment

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.businesslayer.IdeaFragmentInteractor
import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.di.App
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.HolderController
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class IdeaFragmentModel : ViewModel() {

    @Inject
    lateinit var interactor: IdeaFragmentInteractor

    @Inject
    lateinit var adapter: ItemAdapter

    lateinit var userManager: UserManager

    private val mIdea = BehaviorSubject.create<Idea>()
    val idea: Observable<Idea>
        get() = mIdea.hide()

    var taskId = 0L

    val disposables = mutableListOf<Disposable>()

    init {
        App.ideaComponent.inject(this)
    }

    fun refresh() {
        disposables + interactor.getIdeaById(taskId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { item ->
                mIdea.onNext(item)
            }
    }

    fun update(idea: Idea) {
        disposables + interactor.update(idea)
            .subscribeOn(Schedulers.io())
            .subscribe { _ ->
                refresh()
            }
    }

    fun loadAllElements(idea: Idea, nav: NavController) {
        adapter.setAdapterListener(object : HolderController.OnActionClickListener {
            override fun onActionButtonClick(item: Any) {
                attachTo(idea, item)
            }
            override fun onViewClick(item: Any) {
                goToItemFragment(item, nav)
            }
        })
        disposables + interactor.getAllElements()
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
                .subscribe { _ -> refresh() }
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

    private fun goToItemFragment(item: Any, nav: NavController) {
        val bundle = Bundle()
        if (item is Step) {
            bundle.putLong(MainFragment.STEP_ID, item.id)
            nav.navigate(R.id.stepFragment, bundle)
        }
        if (item is Task) {
            bundle.putLong(MainFragment.TASK_ID, item.id)
            nav.navigate(R.id.taskFragment, bundle)
        }
        if (item is Idea) {
            bundle.putLong(MainFragment.IDEA_ID, item.id)
            nav.navigate(R.id.ideaFragment, bundle)
        }
        if (item is KeyResult) {
            bundle.putLong(MainFragment.KEY_ID, item.id)
            nav.navigate(R.id.keyFragment, bundle)
        }
    }
}