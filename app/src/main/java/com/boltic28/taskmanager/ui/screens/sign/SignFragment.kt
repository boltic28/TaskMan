package com.boltic28.taskmanager.ui.screens.sign

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.ui.base.BaseFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_sign.*

class SignFragment : BaseFragment<SignFragmentModel>(R.layout.fragment_sign) {

    private var disposable = Disposables.disposed()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disposable = model.userManager.user
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { user ->
                if (user.id.isNotEmpty()) {
                    model.toMainFragment(findNavController())
                    setToolbarText(user.email)
                } else {
                    turnOnButtonSignIn()
                    setToolbarText(resources.getString(R.string.app_name))
                }
            }
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.dispose()
        model.disposable.dispose()
    }

    private fun initView() {
        sign_in_button.setOnClickListener {
            hideKeyboard()
            if (isFieldsFillRight()) {
                turnOffButtonSignIn()
                model.userManager.signIn(
                    sign_mail.text.toString(), sign_password.text.toString()
                )
            }
        }

        sign_up_button.setOnClickListener {
            if (isFieldsFillRight()) {
                model.userManager.create(
                    sign_mail.text.toString(), sign_password.text.toString()
                )
            }
        }
    }

    private fun turnOffButtonSignIn() {
        sign_in_button.isEnabled = false
        sign_in_button.text = ""
        sign_progress.show()
    }

    private fun turnOnButtonSignIn() {
        sign_in_button.isEnabled = true
        sign_in_button.text = resources.getString(R.string.sign_in)
        sign_progress.hide()
    }

    private fun isFieldsFillRight(): Boolean =
        if (sign_mail.text.toString().contains('@') &&
            sign_password.text.toString().isNotEmpty()
        ) {
            true
        } else {
            model.messenger.showMessage(resources.getString(R.string.tip_input_right_data))
            sign_mail.setBackgroundResource(R.drawable.bg_sign_bad_input)
            false
        }
}