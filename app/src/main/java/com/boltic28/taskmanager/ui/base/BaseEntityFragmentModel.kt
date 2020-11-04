package com.boltic28.taskmanager.ui.base

import android.os.Bundle
import androidx.navigation.NavController
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

abstract class BaseEntityFragmentModel<T>: BaseViewModel() {

    protected val mItem = BehaviorSubject.create<T>()
    val item: Observable<T>
        get() = mItem.hide()

    var itemId = 0L
    var isItemsElementIntoRecycler = false

    val disposables = mutableListOf<Disposable>()

    abstract fun refresh()

    protected fun goToItemFragment(item: Any, nav: NavController){
        val bundle = Bundle()
        if (item is Goal){
            bundle.putLong(MainFragment.GOAL_ID, item.id)
            nav.navigate(R.id.goalFragment, bundle)
        }
        if (item is Step){
            bundle.putLong(MainFragment.STEP_ID, item.id)
            nav.navigate(R.id.stepFragment, bundle)
        }
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