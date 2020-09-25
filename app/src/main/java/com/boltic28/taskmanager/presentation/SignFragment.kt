package com.boltic28.taskmanager.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.dagger.AppDagger
import com.boltic28.taskmanager.presentation.MainActivity.Companion.TAG
import com.boltic28.taskmanager.utils.Messenger
import com.boltic28.taskmanager.signin.FireUserManager
import com.boltic28.taskmanager.signin.UserIn
import com.google.firebase.auth.FirebaseUser
import io.reactivex.disposables.Disposables
import kotlinx.android.synthetic.main.activity_sign.sign_in_button
import kotlinx.android.synthetic.main.activity_sign.sign_mail
import kotlinx.android.synthetic.main.activity_sign.sign_password
import kotlinx.android.synthetic.main.activity_sign.sign_result
import kotlinx.android.synthetic.main.activity_sign.sign_up_button
import kotlinx.android.synthetic.main.fragment_sign.*
import javax.inject.Inject

class SignFragment : Fragment(R.layout.fragment_sign) {

    companion object{
        const val FR_TAG = "sign_frag"
    }

    @Inject
    lateinit var messenger: Messenger

    private val model: SignFragmentModel by lazy {
        ViewModelProviders.of(this).get(
            SignFragmentModel::class.java
        )
    }

    private var disposable = Disposables.disposed()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AppDagger.component.injectFragment(this)
        model.userManager = FireUserManager.getInstance(requireActivity())
        disposable = model.userManager.user.
            subscribe {
                Log.d(TAG, "loading....")
                checkUserdata(it)
            }
        setOnButtons()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.dispose()
    }

    private fun checkUserdata(user: UserIn){
        if (user.id.isNotEmpty()){
            sign_mail.setText(user.email)
            sign_result.text = "you work like ${user.name}"
            sign_in_button.visibility = View.GONE
            sign_up_button.visibility = View.GONE
            sign_out_button.visibility = View.VISIBLE
        }else{
            sign_mail.hint = "E-mail"
            sign_result.text = "you not logged"
            sign_in_button.visibility = View.VISIBLE
            sign_up_button.visibility = View.VISIBLE
            sign_out_button.visibility = View.GONE
        }
    }

    private fun setOnButtons() {
        sign_in_button.setOnClickListener {
            if (isFieldsFillRight()) {
                model.userManager.signIn(
                    sign_mail.text.toString()
                    , sign_password.text.toString()
                )
            }
        }

        sign_up_button.setOnClickListener {
            if (isFieldsFillRight()) {
                model.userManager.create(
                    sign_mail.text.toString()
                    , sign_password.text.toString()
                )
            }
        }

        sign_out_button.setOnClickListener {
            model.userManager.signOut()
        }
    }

    private fun isFieldsFillRight(): Boolean =
        if (sign_mail.text.toString().contains('@') &&
            sign_password.text.toString().isNotEmpty()
        ) {
            true
        } else {
            messenger.showMessage("input right data")
            false
        }

}