package com.boltic28.taskmanager.ui.screens.stepfragment

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.ui.base.BaseEntityFragment
import com.boltic28.taskmanager.ui.screens.activity.ActivityHelper
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment.Companion.STEP_ID
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_step.*

class StepFragment : BaseEntityFragment<StepFragmentModel>(R.layout.fragment_step, STEP_ID) {

    override fun initView() {
        setButtonsBack()
        model.disposables + model.item
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ step ->
                if (step.id != 0L) {
                    (activity as? ActivityHelper)?.setToolbarText(step.name)
                    step_fr_name.text = fetchName(step.name)
                    step_fr_start_date.text = fetchDate(step.date)
                    step_fr_image.setImageResource(R.drawable.step_ph)
                    step_fr_isDone_icon.visibility = fetchVisibility(step.isDone)
                    step_fr_status.text = fetchStatus(step.isStarted, step.isDone)
                    step_fr_description_content.text = step.description
                    step_fr_close_date_content.text = fetchDate(step.dateClose)
                    step_fr_recycler.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    step_fr_recycler.adapter = model.adapter
                    step_fr_button_action.setOnClickListener { loadDataIntoRecycler(step) }

                    setProgress(step.progress)
                    setButtonOwner(step)
                    loadDataIntoRecycler(step)
                }
            }, {

            })
    }

    private fun setProgress(progress: Progress) {
        step_fr_progress.text = resources.getString(R.string.progres_00, 0)
        step_fr_progress_20.setImageResource(R.drawable.bg_progress_off)
        step_fr_progress_40.setImageResource(R.drawable.bg_progress_off)
        step_fr_progress_60.setImageResource(R.drawable.bg_progress_off)
        step_fr_progress_80.setImageResource(R.drawable.bg_progress_off)
        step_fr_progress_100.setImageResource(R.drawable.bg_progress_off)
        if (progress.value >= Progress.PROGRESS_20.value) {
            step_fr_progress_20.setImageResource(R.drawable.bg_progress_on)
            step_fr_progress.text = resources.getString(R.string.progres_00, 20)
        }
        if (progress.value >= Progress.PROGRESS_40.value) {
            step_fr_progress_40.setImageResource(R.drawable.bg_progress_on)
            step_fr_progress.text = resources.getString(R.string.progres_00, 40)
        }
        if (progress.value >= Progress.PROGRESS_60.value) {
            step_fr_progress_60.setImageResource(R.drawable.bg_progress_on)
            step_fr_progress.text = resources.getString(R.string.progres_00, 60)
        }
        if (progress.value >= Progress.PROGRESS_80.value) {
            step_fr_progress_80.setImageResource(R.drawable.bg_progress_on)
            step_fr_progress.text = resources.getString(R.string.progres_00, 80)
        }
        if (progress.value == Progress.DONE.value) {
            step_fr_progress_100.setImageResource(R.drawable.bg_progress_on)
            step_fr_progress.text = resources.getString(R.string.progres_00, 100)
        }
    }

    private fun setButtonsBack() {
        step_fr_button_back.setOnClickListener {
            activity?.onBackPressed()
        }
        step_fr_button_home.setOnClickListener {
            findNavController().navigate(R.id.mainFragment)
        }
    }

    private fun setButtonOwner(step: Step) {
        if (step.goalId != 0L) {
            step_fr_owner_button.setImageResource(R.drawable.ic_unlink)
            step_fr_owner_button.setOnClickListener {
                model.update(step.copy(goalId = 0L))
            }
            model.disposables + model.interactor.getGoalById(step.goalId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    step_fr_relative_owner.text = it.name
                }, {

                })
        } else {
            step_fr_owner_button.setImageResource(R.drawable.ic_link)
            step_fr_relative_owner.text = resources.getString(R.string.not_attached)
            step_fr_owner_button.setOnClickListener {
                model.isItemsElementIntoRecycler = false
                model.loadGoalsIntoAdapter(step, findNavController())
            }
        }
    }

    private fun loadDataIntoRecycler(step: Step) {
        if (model.isItemsElementIntoRecycler) {
            model.isItemsElementIntoRecycler = false
            model.loadFreeElementIntoAdapter(findNavController())
            step_fr_its_elements.text = resources.getString(R.string.free_elements)
            step_fr_button_action.setImageResource(R.drawable.ic_elements)
        } else {
            model.isItemsElementIntoRecycler = true
            model.loadStepsElementIntoAdapter(step, findNavController())
            step_fr_its_elements.text = resources.getString(R.string.its_elements)
            step_fr_button_action.setImageResource(R.drawable.ic_add_item)
        }
    }
}