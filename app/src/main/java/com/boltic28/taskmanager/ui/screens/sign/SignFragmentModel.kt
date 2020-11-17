package com.boltic28.taskmanager.ui.screens.sign

import androidx.navigation.NavController
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.businesslayer.interactors.SignFragmentInteractor
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.base.BaseViewModel
import com.boltic28.taskmanager.utils.Messenger
import javax.inject.Inject

class SignFragmentModel @Inject constructor(
    override var userManager: UserManager,
    private val interactor: SignFragmentInteractor,
    val messenger: Messenger
): BaseViewModel(){

    fun toMainFragment(navController: NavController){
        interactor.refreshAllData()
        navController.navigate(R.id.action_signFragment_to_mainFragment)
    }
}