package com.boltic28.taskmanager.ui.screens.goalfragment

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.ui.base.BaseEntityFragment
import com.boltic28.taskmanager.ui.screens.activity.ActivityHelper
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment.Companion.GOAL_ID
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_goal.*

class GoalFragment : BaseEntityFragment<GoalFragmentModel>(R.layout.fragment_goal, GOAL_ID) {



    override fun initView() {
        setButtonsBack()
        model.disposables + model.item
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ goal ->
                if (goal.id != 0L) {
                    (activity as? ActivityHelper)?.setToolbarText(goal.name)
                    goal_fr_name.text = fetchName(goal.name)
                    goal_fr_image.setImageResource(R.drawable.goal_ph)
                    goal_fr_isDone_icon.visibility = fetchVisibility(goal.isDone)
                    goal_fr_status.text = fetchStatus(goal.isStarted, goal.isDone)
                    goal_fr_description_content.text = goal.description
                    goal_fr_start_date.text = fetchDate(goal.date)
                    goal_fr_close_date_content.text = fetchDate(goal.dateClose)
                    goal_fr_recycler.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    goal_fr_recycler.adapter = model.adapter
                    goal_fr_button_action.setOnClickListener {
                        loadDataIntoRecycler(goal)
                    }

                    setProgress(goal.progress)
                    loadDataIntoRecycler(goal)
                }
            }, {

            })
    }

    private fun setButtonsBack() {
        goal_fr_button_back.setOnClickListener {
            activity?.onBackPressed()
        }
        goal_fr_button_home.setOnClickListener {
            findNavController().navigate(R.id.mainFragment)
        }
    }

    private fun setProgress(progress: Progress) {
        goal_fr_progress.text = resources.getString(R.string.progres_00, 0)
        if (progress.value >= Progress.PROGRESS_20.value) {
            goal_fr_progress_20.setImageResource(R.drawable.bg_progress_on)
            goal_fr_progress.text = resources.getString(R.string.progres_00, 20)
        }
        if (progress.value >= Progress.PROGRESS_40.value) {
            goal_fr_progress_40.setImageResource(R.drawable.bg_progress_on)
            goal_fr_progress.text = resources.getString(R.string.progres_00, 40)
        }
        if (progress.value >= Progress.PROGRESS_60.value) {
            goal_fr_progress_60.setImageResource(R.drawable.bg_progress_on)
            goal_fr_progress.text = resources.getString(R.string.progres_00, 60)
        }
        if (progress.value >= Progress.PROGRESS_80.value) {
            goal_fr_progress_80.setImageResource(R.drawable.bg_progress_on)
            goal_fr_progress.text = resources.getString(R.string.progres_00, 80)
        }
        if (progress.value == Progress.DONE.value) {
            goal_fr_progress_100.setImageResource(R.drawable.bg_progress_on)
            goal_fr_progress.text = resources.getString(R.string.progres_00, 100)
        }
    }

    private fun loadDataIntoRecycler(goal: Goal) {
        if (model.isItemsElementIntoRecycler) {
            model.isItemsElementIntoRecycler = false
            model.loadFreeElementIntoAdapter(findNavController())
            goal_fr_its_elements.text = resources.getString(R.string.free_elements)
            goal_fr_button_action.setImageResource(R.drawable.ic_elements)
        } else {
            model.isItemsElementIntoRecycler = true
            model.loadGoalsElementIntoAdapter(goal, findNavController())
            goal_fr_its_elements.text = resources.getString(R.string.its_elements)
            goal_fr_button_action.setImageResource(R.drawable.ic_add_item)
        }
    }
}