package com.boltic28.taskmanager.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.dagger.AppDagger
import com.boltic28.taskmanager.utils.Messenger
import com.boltic28.taskmanager.signin.FireUserManager
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment: Fragment(R.layout.fragment_main) {

    companion object{
        const val TAG = "main_frag"
    }

    @Inject
    lateinit var messenger: Messenger

    private val model: MainFragmentModel by lazy { ViewModelProviders.of(this).get(
        MainFragmentModel::class.java)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AppDagger.component.injectFragment(this)
        model.userManager = FireUserManager.getInstance(requireActivity())
        setOnButtons()
    }

    private fun setOnButtons(){
        sign_button.setOnClickListener {
            (activity as? MainActivity)?.toSingFragment()
        }
    }
}