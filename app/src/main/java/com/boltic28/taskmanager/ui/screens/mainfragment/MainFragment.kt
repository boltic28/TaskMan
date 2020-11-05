package com.boltic28.taskmanager.ui.screens.mainfragment

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.ui.adapter.controllers.HolderController
import com.boltic28.taskmanager.ui.base.BaseFragment
import com.boltic28.taskmanager.ui.screens.activity.ActivityHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment<MainFragmentModel>(R.layout.fragment_main) {

    companion object {
        const val GOAL_ID = "goalId"
        const val STEP_ID = "stepId"
        const val IDEA_ID = "ideaId"
        const val TASK_ID = "taskId"
        const val KEY_ID = "keyId"
    }

    override fun onResume() {
        super.onResume()
        if (model.userManager.isUserSigned()) {
            model.disposables + model.userManager.user
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    (activity as? ActivityHelper)?.setToolbarText(it.email)
                }
            loadGoals()
        } else {
            (activity as? ActivityHelper)?.setToolbarText(resources.getString(R.string.app_name))
            findNavController().navigate(R.id.signFragment)
        }

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

        main_add_button.setOnClickListener { findNavController().navigate(R.id.creatorFragment) }
        main_free_goals.setOnClickListener { loadGoals() }
        main_free_keys.setOnClickListener { loadKeys() }
        main_free_tasks.setOnClickListener { loadTasks() }
        main_free_ideas.setOnClickListener { loadIdeas() }
        main_free_steps.setOnClickListener { loadSteps() }
    }

    private fun loadGoals() {
        model.adapter.setAdapterListener(object : HolderController.OnActionClickListener {
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

    private fun loadSteps() {
        model.adapter.setAdapterListener(object : HolderController.OnActionClickListener {
            override fun onActionButtonClick(item: Any) {}
            override fun onViewClick(item: Any) {
                item as Step
                val bundle = Bundle()
                bundle.putLong(STEP_ID, item.id)
                findNavController().navigate(R.id.stepFragment, bundle)
            }
        })
        model.loadSteps()
    }

    private fun loadTasks() {
        model.adapter.setAdapterListener(object : HolderController.OnActionClickListener {
            override fun onActionButtonClick(item: Any) {
                item as Task
                if (item.isStarted) {
                    model.update(item.copy(isDone = true))
                } else {
                    model.update(item.copy(isStarted = true))
                }
            }

            override fun onViewClick(item: Any) {
                item as Task
                val bundle = Bundle()
                bundle.putLong(TASK_ID, item.id)
                findNavController().navigate(R.id.taskFragment, bundle)
            }
        })
        model.loadTasks()
    }

    private fun loadIdeas() {
        model.adapter.setAdapterListener(object : HolderController.OnActionClickListener {
            override fun onActionButtonClick(item: Any) {
                // todo go to convert fragment
            }

            override fun onViewClick(item: Any) {
                item as Idea
                val bundle = Bundle()
                bundle.putLong(IDEA_ID, item.id)
                findNavController().navigate(R.id.ideaFragment, bundle)
            }
        })
        model.loadIdeas()
    }

    private fun loadKeys() {
        model.adapter.setAdapterListener(object : HolderController.OnActionClickListener {
            override fun onActionButtonClick(item: Any) {}
            override fun onViewClick(item: Any) {
                item as KeyResult
                val bundle = Bundle()
                bundle.putLong(KEY_ID, item.id)
                findNavController().navigate(R.id.ideaFragment, bundle)
            }
        })
        model.loadKeys()
    }
}