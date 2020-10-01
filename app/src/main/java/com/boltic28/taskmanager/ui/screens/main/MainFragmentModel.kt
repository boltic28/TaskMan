package com.boltic28.taskmanager.ui.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.dagger.AppDagger
import com.boltic28.taskmanager.datalayer.room.goal.GoalService
import com.boltic28.taskmanager.ui.screens.MainActivity
import com.boltic28.taskmanager.signtools.FireUserManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainFragmentModel : ViewModel() {

    @Inject
    lateinit var goalService: GoalService

    @Inject
    lateinit var adapter: ItemAdapter

    lateinit var userManager: FireUserManager

    var disposable = Disposables.disposed()

    init {
        AppDagger.component.injectModel(this)
    }

    fun checkGoals(): Disposable =
        goalService.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapter.refreshData(it)
            }, {
                Log.d(MainActivity.TAG, "getAll - Goal error \n ->$it")
            })
}