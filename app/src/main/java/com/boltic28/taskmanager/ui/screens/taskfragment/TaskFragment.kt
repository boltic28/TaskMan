package com.boltic28.taskmanager.ui.screens.taskfragment

import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.ui.base.BaseEntityFragment
import com.boltic28.taskmanager.ui.screens.activity.ActivityHelper
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment.Companion.TASK_ID
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_task.*
import java.time.format.DateTimeFormatter

class TaskFragment : BaseEntityFragment<TaskFragmentModel>(R.layout.fragment_task, TASK_ID) {

    override fun initView() {
        model.disposables + model.item
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { task ->
                if (task.id != 0L) {
                    (activity as? ActivityHelper)?.setToolbarText(task.name)
                    task_fr_name.text = task.name
                    task_fr_start_date.text =
                        task.date.format(
                            DateTimeFormatter
                                .ofPattern(resources.getString(R.string.dateFormatterForItems))
                        )
                    checkState(task.isStarted)
                    task_fr_finish_content.text =
                        task.dateClose.format(
                            DateTimeFormatter
                                .ofPattern(resources.getString(R.string.dateFormatterForItems))
                        )
                    task_fr_description_content.text = task.description
                }
            }
    }

    private fun checkState(isStarted: Boolean) {
        if (isStarted) {
            task_fr_is_started.text = resources.getString(R.string.in_progress)
            task_fr_add_action.text = resources.getString(R.string.done_it)
            task_fr_add_action.setOnClickListener {
                //TODO
            }
        } else {
            task_fr_is_started.text = resources.getString(R.string.not_started)
            task_fr_add_action.text = resources.getString(R.string.start_it)
            task_fr_add_action.setOnClickListener {
                //TODO
            }
        }
    }
}