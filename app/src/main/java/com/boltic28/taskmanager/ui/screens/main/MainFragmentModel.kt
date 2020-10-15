package com.boltic28.taskmanager.ui.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.di.AppDagger
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.ui.screens.MainActivity
import com.boltic28.taskmanager.signtools.FireUserManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainFragmentModel : ViewModel() {

    @Inject
    lateinit var goalRepository: GoalRepository

    @Inject
    lateinit var adapter: ItemAdapter

    lateinit var userManager: FireUserManager

    var disposable = Disposables.disposed()

    init {
        AppDagger.component.injectModel(this)
    }

    fun checkGoals(): Disposable =
        goalRepository.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapter.refreshData(it)
            }, {
                Log.d(MainActivity.TAG, "getAll - Goal error \n ->$it")
            })
}