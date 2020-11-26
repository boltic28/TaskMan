package com.boltic28.taskmanager.ui.base

import android.os.Bundle
import androidx.navigation.NavController
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.ui.constant.*
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

abstract class BaseEntityFragmentModel<T> : BaseViewModel() {

    abstract val extraKey: String

    protected val mItem = BehaviorSubject.create<T>()
    val item: Observable<T>
        get() = mItem.hide()

    var itemId = NO_ID
    var isLoadFreeElements = false

    val disposables = mutableListOf<Disposable>()

    abstract fun refresh()
    abstract fun update(item: BaseItem)
    abstract fun delete(item: BaseItem, nav: NavController)


    protected fun goToItemFragment(item: BaseItem, nav: NavController) {
        val bundle = Bundle()
        when (item) {
            is Goal -> {
                bundle.putLong(GOAL_EXTRA, item.id)
                nav.navigate(R.id.goalFragment, bundle)
            }
            is Step -> {
                bundle.putLong(STEP_EXTRA, item.id)
                nav.navigate(R.id.stepFragment, bundle)
            }
            is Task -> {
                bundle.putLong(TASK_EXTRA, item.id)
                nav.navigate(R.id.taskFragment, bundle)
            }
            is Idea -> {
                bundle.putLong(IDEA_EXTRA, item.id)
                nav.navigate(R.id.ideaFragment, bundle)
            }
            is KeyResult -> {
                bundle.putLong(KEY_EXTRA, item.id)
                nav.navigate(R.id.keyFragment, bundle)
            }
        }
    }
}