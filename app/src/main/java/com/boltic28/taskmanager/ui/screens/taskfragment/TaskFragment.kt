package com.boltic28.taskmanager.ui.screens.taskfragment

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.ui.base.BaseEntityFragment
import com.boltic28.taskmanager.ui.constant.NO_ID
import com.boltic28.taskmanager.ui.constant.TASK_EXTRA
import com.boltic28.taskmanager.ui.screens.activity.ActivityHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_task.*

class TaskFragment : BaseEntityFragment<TaskFragmentModel>(R.layout.fragment_task, TASK_EXTRA) {

    override fun initView() {
        setButtonsBack()
        model.disposables + model.item
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { task ->
                if (task.id != NO_ID) {
                    (activity as? ActivityHelper)?.setToolbarText(task.name)
                    task_fr_name.text = fetchName(task.name)
                    task_fr_isDone_icon.visibility = fetchVisibility(task.isDone)
                    task_fr_start_date.text = fetchDate(task.date)
                    task_fr_status.text = fetchStatus(task.isStarted, task.isDone)
                    task_fr_description_content.text = task.description
                    task_fr_close_date_content.text = fetchDate(task.dateClose)
                    task_fr_cycle_content.text = task.cycle.value
                    task_fr_button_action.isActivated = true

                    setButtonOwner(task)
                    checkState(task)
                }
            }
    }

    private fun setButtonsBack() {
        task_fr_button_back.setOnClickListener {
            activity?.onBackPressed()
        }
        task_fr_button_home.setOnClickListener {
            findNavController().navigate(R.id.mainFragment)
        }
    }

    private fun setButtonOwner(task: Task) {
        task_fr_recycler.visibility = View.INVISIBLE
        task_fr_its_elements.visibility = View.INVISIBLE
        if ((task.goalId != NO_ID) || (task.stepId != NO_ID) || (task.keyId != NO_ID)) {
            task_fr_owner_button.setImageResource(R.drawable.ic_unlink)
            task_fr_owner_button.setOnClickListener {
                model.update(
                    task.copy(goalId = NO_ID, stepId = NO_ID, keyId = NO_ID)
                )
            }
            model.disposables + model.getParentName(task)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { ownerName ->
                    task_fr_relative_owner.text = ownerName
                }
        } else {
            task_fr_relative_owner.text = resources.getString(R.string.not_attached)
            task_fr_owner_button.setImageResource(R.drawable.ic_link)
            task_fr_owner_button.setOnClickListener {
                task_fr_recycler.visibility = View.VISIBLE
                task_fr_recycler.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                task_fr_recycler.adapter = model.adapter
                model.loadParentsElements(task, findNavController())
            }
        }
    }

    private fun checkState(task: Task) {
        if (model.isAttention(task)){
            task_fr_isDone_icon.visibility = View.VISIBLE
            task_fr_isDone_icon.setImageResource(R.drawable.ic_attention)
        }

        if (task.isDone) {
            task_fr_button_action.isActivated =false
        }

        if (task.isStarted) {
            task_fr_button_action.setImageResource(R.drawable.ic_done)
            task_fr_button_action.setOnClickListener {
                model.update(task.copy(isDone = true))
            }
        } else {
            task_fr_button_action.setImageResource(R.drawable.ic_start)
            task_fr_button_action.setOnClickListener {
                model.update(task.copy(isStarted = true))
            }
        }

    }
}