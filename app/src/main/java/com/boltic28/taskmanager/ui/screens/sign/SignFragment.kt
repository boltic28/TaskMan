package com.boltic28.taskmanager.ui.screens.sign

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.di.AppDagger
import com.boltic28.taskmanager.ui.screens.main.MainFragment
import com.boltic28.taskmanager.utils.Messenger
import com.boltic28.taskmanager.signtools.FireUserManager
import com.boltic28.taskmanager.signtools.UserIn
import io.reactivex.disposables.Disposables
import kotlinx.android.synthetic.main.fragment_sign.*
import javax.inject.Inject

class SignFragment : Fragment(R.layout.fragment_sign) {

    companion object{
        const val TAG = "mainActivity_test"
    }

    @Inject
    lateinit var messenger: Messenger

    private val model: SignFragmentModel by lazy {
        ViewModelProviders.of(this).get(
            SignFragmentModel::class.java
        )
    }

    private var disposable = Disposables.disposed()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppDagger.component.injectFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(MainFragment.TAG, "-> signFragment")

        model.userManager = FireUserManager.getInstance(requireActivity())
        disposable = model.userManager.user
            .subscribe {user ->
                if (user.id.isNotEmpty()){
                    findNavController().navigate(R.id.action_signFragment_to_mainFragment)
                }else {
                    turnOnButtonSignIn()
                    checkUserdata(user)
                }
            }
        setOnButtons()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.dispose()
    }

    private fun checkUserdata(user: UserIn){
        Log.d(TAG, "loading users data to view....")
        if (user.id.isNotEmpty()){
            sign_mail.setText(user.email)
            sign_text.text = resources.getString(R.string.sign_signout_text, user.name)
            sign_in_button.visibility = View.GONE
            sign_up_button.visibility = View.GONE
            sign_out_button.visibility = View.VISIBLE
        }else{
            sign_mail.hint = resources.getString(R.string.sign_email)
            sign_text.text = resources.getString(R.string.sign_signin_or_register)
            sign_in_button.visibility = View.VISIBLE
            sign_up_button.visibility = View.VISIBLE
            sign_out_button.visibility = View.GONE
        }
    }

    private fun setOnButtons() {
        sign_in_button.setOnClickListener {
            if (isFieldsFillRight()) {
                turnOffButtonSignIn()
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

    private fun turnOffButtonSignIn(){
        sign_in_button.isEnabled = false
        sign_in_button.text = ""
        sign_progress.show()
    }

    private fun turnOnButtonSignIn(){
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
            messenger.showMessage(resources.getString(R.string.tip_input_right_data))
            sign_mail.setBackgroundResource(R.drawable.bg_sign_bad_input)
            false
        }
}