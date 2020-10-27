package com.boltic28.taskmanager.ui.screens.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.signtools.FireUserManager
import com.boltic28.taskmanager.ui.adapter.controllers.HolderController
import com.boltic28.taskmanager.ui.screens.ActivityHelper
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        const val TAG = "mainActivity_test"
        const val GOAL_ID = "goalId"
    }

    private val model: MainFragmentModel by lazy {
        ViewModelProviders.of(this).get(
            MainFragmentModel::class.java
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "-> mainFragment")

        checkUser()
        initView()
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
        main_free_goals.setOnClickListener {
            loadGoals()
        }
        main_free_keys.setOnClickListener { model.loadKeys()
            model.adapter.setAdapterListener(object : HolderController.OnActionClickListener{
                override fun onActionButtonClick(item: Any) {}
                override fun onViewClick(item: Any) {

                }

            })}
        main_free_tasks.setOnClickListener { model.loadTasks()
            model.adapter.setAdapterListener(object : HolderController.OnActionClickListener{
                override fun onActionButtonClick(item: Any) {}
                override fun onViewClick(item: Any) {

                }

            })}
        main_free_ideas.setOnClickListener { model.loadIdeas()
            model.adapter.setAdapterListener(object : HolderController.OnActionClickListener{
                override fun onActionButtonClick(item: Any) {}
                override fun onViewClick(item: Any){
                }

            })}
        main_free_steps.setOnClickListener { model.loadSteps()
            model.adapter.setAdapterListener(object : HolderController.OnActionClickListener{
                override fun onActionButtonClick(item: Any) {}
                override fun onViewClick(item: Any) {

                }

            })}
    }

    private fun checkUser() {
        model.disposables + FireUserManager.getInstance(requireActivity()).user
            .subscribe({ user ->
                if (user.id.isEmpty()) {
                    (activity as? ActivityHelper)?.setToolbarText(resources.getString(R.string.app_name))
                    findNavController().navigate(R.id.signFragment)
                } else {
                    (activity as? ActivityHelper)?.setToolbarText(user.email)
                    loadGoals()
                }
            }, {
                Log.d(TAG, it.toString())
            })
    }

    private fun loadGoals(){
        model.adapter.setAdapterListener(object : HolderController.OnActionClickListener{
            override fun onActionButtonClick(item: Any) {}
            override fun onViewClick(item: Any) {
                item as Goal
                val bundle = Bundle()
                bundle.putLong(GOAL_ID, item.id)
                findNavController().navigate(R.id.goalFragment, bundle)
            }
        })
        model.loadGoals()
    }
}