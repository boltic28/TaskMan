package com.boltic28.taskmanager.ui.base

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import android.util.Log
import com.boltic28.taskmanager.businesslayer.syncmanager.ACCOUNT_TYPE
import com.boltic28.taskmanager.ui.screens.activity.ActivityHelper
import com.boltic28.taskmanager.ui.screens.sign.SignFragment
import dagger.android.support.DaggerFragment
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel>(layout: Int) : DaggerFragment(layout) {

    @Inject
    lateinit var model: VM

    protected lateinit var mAccount: Account

    fun checkUserAccount(){
        mAccount = createSyncAccount()
    }

    fun hideKeyboard(){
        (activity as? ActivityHelper)?.hideKeyBoard()
    }

    fun setToolbarText(header: String){
        (activity as? ActivityHelper)?.setToolbarText(header)
    }

    fun showProgressBar(){
        (activity as? ActivityHelper)?.showProgressBar()
    }

    fun hideProgressBar(){
        (activity as? ActivityHelper)?.hideProgressBar()
    }

    protected fun createSyncAccount(): Account {
        val accountManager = activity?.getSystemService(Context.ACCOUNT_SERVICE) as AccountManager
        return Account(model.userManager.userI.email, ACCOUNT_TYPE).also { newAccount ->
            if (accountManager.addAccountExplicitly(newAccount, model.userManager.userI.password, model.userManager.getUserData())) {
                Log.d("AccountManager", "account ${model.userManager.userI.email} is creating")
            } else {
                Log.d("AccountManager", "account ${model.userManager.userI.email} is created alredy")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        model.disposables.dispose()
    }
}
