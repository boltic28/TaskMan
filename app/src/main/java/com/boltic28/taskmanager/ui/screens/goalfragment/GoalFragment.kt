package com.boltic28.taskmanager.ui.screens.goalfragment

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.ui.base.BaseEntityFragment
import com.boltic28.taskmanager.ui.screens.ActivityHelper
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment.Companion.GOAL_ID
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_goal.*
import java.time.format.DateTimeFormatter

class GoalFragment : BaseEntityFragment<GoalFragmentModel>(R.layout.fragment_goal, GOAL_ID) {

    override fun initView() {
        model.disposables + model.item
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
        if (model.isItemsElementIntoRecycler) {
            model.isItemsElementIntoRecycler = false
            model.loadFreeElementIntoAdapter(findNavController())
            goal_fr_add_action.text = resources.getString(R.string.to_goals_element)
        } else {
            model.isItemsElementIntoRecycler = true
            model.loadGoalsElementIntoAdapter(goal, findNavController())
            goal_fr_add_action.text = resources.getString(R.string.to_free_elements)
        }
    }
}