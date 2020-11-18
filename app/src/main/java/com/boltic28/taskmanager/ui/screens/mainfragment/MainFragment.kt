package com.boltic28.taskmanager.ui.screens.mainfragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.ui.adapter.controllers.HolderController
import com.boltic28.taskmanager.ui.base.BaseFragment
import com.boltic28.taskmanager.ui.constant.*
import com.boltic28.taskmanager.ui.screens.activity.ActivityHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment<MainFragmentModel>(R.layout.fragment_main) {

    override fun onResume() {
        super.onResume()
        if (model.userManager.isUserSigned()) {
            model.disposables + model.userManager.user
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    (activity as? ActivityHelper)?.setToolbarText(it.email)
                }
            arguments?.let {
                if (it.containsKey(USER_SIGNED)) showRefreshDialog()
            }
            loadElements()
        } else {
            (activity as? ActivityHelper)?.setToolbarText(resources.getString(R.string.app_name))
            findNavController().navigate(R.id.signFragment)
        }

        initView()
        setFilters()
    }

    override fun onStop() {
        super.onStop()
        model.disposables.forEach { it.dispose() }
    }

    private fun loadElements(){
        arguments?.let {
            when(it.getString(LOAD_LIST)){
                KEY_EXTRA -> loadKeys()
                STEP_EXTRA -> loadSteps()
                TASK_EXTRA -> loadTasks()
                IDEA_EXTRA -> loadIdeas()
                else -> loadGoals()
            }
        }
    }

    private fun showRefreshDialog() {
        AlertDialog.Builder(context)
            .setMessage(resources.getString(R.string.data_refreshed))
            .setPositiveButton(resources.getString(R.string.ok)) { _, _ -> loadGoals() }
            .setTitle(resources.getString(R.string.app_name))
            .setIcon(R.drawable.wow_ph)
            .create()
            .show()
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
            override fun isNeedToShowConnection(): Boolean = false
            override fun onActionButtonClick(item: BaseItem) {}
            override fun onViewClick(item: BaseItem) {
                item as Goal
                val bundle = Bundle()
                bundle.putLong(GOAL_EXTRA, item.id)
                findNavController().navigate(R.id.goalFragment, bundle)
            }
        })
        model.loadGoals()
    }

    private fun loadSteps() {
        model.adapter.setAdapterListener(object : HolderController.OnActionClickListener {
            override fun isNeedToShowConnection(): Boolean = true
            override fun onActionButtonClick(item: BaseItem) {}
            override fun onViewClick(item: BaseItem) {
                item as Step
                val bundle = Bundle()
                bundle.putLong(STEP_EXTRA, item.id)
                findNavController().navigate(R.id.stepFragment, bundle)
            }
        })
        model.loadSteps()
    }

    private fun loadTasks() {
        model.adapter.setAdapterListener(object : HolderController.OnActionClickListener {
            override fun isNeedToShowConnection(): Boolean = true
            override fun onActionButtonClick(item: BaseItem) {
                item as Task
                if (item.isStarted) {
                    model.update(item.copy(isDone = true))
                } else {
                    model.update(item.copy(isStarted = true))
                }
            }

            override fun onViewClick(item: BaseItem) {
                item as Task
                val bundle = Bundle()
                bundle.putLong(TASK_EXTRA, item.id)
                findNavController().navigate(R.id.taskFragment, bundle)
            }
        })
        model.loadTasks()
    }

    private fun loadIdeas() {
        model.adapter.setAdapterListener(object : HolderController.OnActionClickListener {
            override fun isNeedToShowConnection(): Boolean = true
            override fun onActionButtonClick(item: BaseItem) {
                item as Idea
                val bundle = Bundle()
                bundle.putLong(IDEA_EXTRA, item.id)
                bundle.putBoolean(IDEA_CONVERTER, true)
                findNavController().navigate(R.id.ideaFragment, bundle)
            }

            override fun onViewClick(item: BaseItem) {
                item as Idea
                val bundle = Bundle()
                bundle.putLong(IDEA_EXTRA, item.id)
                findNavController().navigate(R.id.ideaFragment, bundle)
            }
        })
        model.loadIdeas()
    }

    private fun loadKeys() {
        model.adapter.setAdapterListener(object : HolderController.OnActionClickListener {
            override fun isNeedToShowConnection(): Boolean = true
            override fun onActionButtonClick(item: BaseItem) {}
            override fun onViewClick(item: BaseItem) {
                item as KeyResult
                val bundle = Bundle()
                bundle.putLong(KEY_EXTRA, item.id)
                findNavController().navigate(R.id.keyFragment, bundle)
            }
        })
        model.loadKeys()
    }

    private fun setFilters() {
        main_search_field.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                val predicate = main_search_field.text.toString()
                if (predicate.isNotEmpty()) {
                    model.adapter.filter(predicate)
                } else {
                    model.adapter.clearFilter()
                }
            }
        })
    }
}