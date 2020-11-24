package com.boltic28.taskmanager.ui.screens.sign

import android.os.Bundle
import androidx.navigation.NavController
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.businesslayer.interactors.SignFragmentInteractor
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.base.BaseViewModel
import com.boltic28.taskmanager.ui.constant.USER_SIGNED
import com.boltic28.taskmanager.utils.Messenger
import io.reactivex.disposables.Disposables
import javax.inject.Inject

class SignFragmentModel @Inject constructor(
    override var userManager: UserManager,
    private val interactor: SignFragmentInteractor,
    val messenger: Messenger
) : BaseViewModel() {

    var disposable = Disposables.disposed()

    fun toMainFragment(navController: NavController) {
        val bundle = Bundle()
        bundle.putString(USER_SIGNED, USER_SIGNED)
        navController.navigate(R.id.action_signFragment_to_mainFragment, bundle)
    }
}