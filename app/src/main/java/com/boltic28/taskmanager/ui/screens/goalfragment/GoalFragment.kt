package com.boltic28.taskmanager.ui.screens.goalfragment

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
import com.boltic28.taskmanager.ui.screens.ActivityHelper
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment.Companion.GOAL_ID
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_goal.*
import java.time.format.DateTimeFormatter

class GoalFragment : Fragment(R.layout.fragment_goal) {

    private val model: GoalFragmentModel by lazy {
        ViewModelProviders.of(this).get(GoalFragmentModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(MainFragment.TAG, "-> mainFragment")
        model.userManager = FireUserManager.getInstance(requireActivity())

        val goalId: Long? = arguments?.getLong(GOAL_ID)

        if (!model.userManager.isUserSigned()) {
            (activity as? ActivityHelper)?.setToolbarText(resources.getString(R.string.app_name))
            findNavController().navigate(R.id.signFragment)
        }

        if (goalId != null) {
            model.goalId = goalId
            model.refreshGoal()
        } else {
            findNavController().navigate(R.id.mainFragment)
        }
        initView()
    }

    override fun onStop() {
        super.onStop()
        model.disposables.forEach { it.dispose() }
    }

    private fun initView() {
        model.disposables + model.goal
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ goal ->
                if (goal.id != 0L) {
                    (activity as? ActivityHelper)?.setToolbarText(goal.name)
                    goal_fr_description.text = goal.description
                    goal_fr_start_date.text =
                        goal.date.format(
                            DateTimeFormatter
                                .ofPattern(resources.getString(R.string.dateFormatterForItems))
                        )
                    goal_fr_percentage.text =
                        resources.getString(R.string.percentage)
                    goal_fr_recycler.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    goal_fr_recycler.adapter = model.adapter
                    loadDataIntoRecycler(goal)
                    goal_fr_add_action.setOnClickListener {
                        loadDataIntoRecycler(goal)
                    }
                }
            }, {

            })
    }

    private fun loadDataIntoRecycler(goal: Goal) {
        if (model.isGoalsElementIntoRecycler) {
            model.isGoalsElementIntoRecycler = false
            model.loadFreeElementIntoAdapter()
            goal_fr_add_action.text = resources.getString(R.string.to_goals_element)
        } else {
            model.isGoalsElementIntoRecycler = true
            model.loadGoalsElementIntoAdapter(goal)
            goal_fr_add_action.text = resources.getString(R.string.to_free_elements)
        }
    }
}