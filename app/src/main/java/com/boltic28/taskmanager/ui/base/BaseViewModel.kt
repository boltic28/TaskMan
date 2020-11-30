package com.boltic28.taskmanager.ui.base

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.signtools.UserManager
import io.reactivex.disposables.Disposable

abstract class BaseViewModel: ViewModel(){

    abstract var userManager: UserManager

    val disposables: MutableList<Disposable> = mutableListOf()
}

fun MutableList<Disposable>.dispose() {
    this.forEach { it.dispose() }
}