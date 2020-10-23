package com.boltic28.taskmanager.ui.screens.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.di.AppDagger
import com.boltic28.taskmanager.signtools.FireUserManager
import com.boltic28.taskmanager.ui.screens.ActivityHelper
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        const val TAG = "mainActivity_test"
    }

    private lateinit var userManager: FireUserManager

    private val model: MainFragmentModel by lazy {
        ViewModelProviders.of(this).get(
            MainFragmentModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppDagger.component.injectFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "-> mainFragment")

        initView()
        userManager = FireUserManager.getInstance(requireActivity())

        checkUser()
    }

    override fun onStop() {
        super.onStop()
        model.disposables.forEach { it.dispose() }
    }

    private fun initView() {
        main_recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        main_recycler.adapter = model.adapter

        main_add_button.setOnClickListener {
            findNavController().navigate(R.id.creatorFragment)
        }
        main_free_goals.setOnClickListener { model.loadGoals() }
        main_free_keys.setOnClickListener { model.loadKeys() }
        main_free_tasks.setOnClickListener { model.loadTasks() }
        main_free_ideas.setOnClickListener { model.loadIdeas() }
        main_free_steps.setOnClickListener { model.loadSteps() }
    }

    // move to activity
    private fun checkUser() {
        model.disposables + userManager.user
            .subscribe({ user ->
                if (user.id.isEmpty()) {
                    Log.d(TAG, "user empty")
                    (activity as? ActivityHelper)?.setToolbarText(resources.getString(R.string.app_name))
                    findNavController().navigate(R.id.signFragment)
                } else {
                    (activity as? ActivityHelper)?.setToolbarText(user.email)
                    model.loadGoals()
                }
            }, {
                Log.d(TAG, it.toString())
            })
    }
}