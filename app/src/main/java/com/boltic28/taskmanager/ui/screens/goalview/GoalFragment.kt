package com.boltic28.taskmanager.ui.screens.goalview

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.signtools.FireUserManager
import com.boltic28.taskmanager.ui.screens.ActivityHelper
import com.boltic28.taskmanager.ui.screens.main.MainFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_goal.*
import java.time.format.DateTimeFormatter

class GoalFragment : Fragment(R.layout.fragment_goal) {

    private val model: GoalFragmentModel by lazy {
        ViewModelProviders.of(this).get(GoalFragmentModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.userManager = FireUserManager.getInstance(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(MainFragment.TAG, "-> mainFragment")

        if (model.userManager.isUserSigned() && savedInstanceState != null) {
            model.goalId = savedInstanceState.getLong("goalID", 0L)
            model.refreshGoal()
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
                        resources.getString(R.string.percentage)// make it correct
                    goal_fr_recycler.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    goal_fr_recycler.adapter = model.adapter
                    model.loadGoalsElementIntoAdapter(goal)
                    goal_fr_add_action.setOnClickListener {
                        model.loadFreeElementIntoAdapter()
                    }
                }
            }, {

            })
    }
}