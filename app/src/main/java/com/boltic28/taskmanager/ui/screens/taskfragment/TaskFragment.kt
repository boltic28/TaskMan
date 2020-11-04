package com.boltic28.taskmanager.ui.screens.taskfragment

import androidx.navigation.fragment.findNavController
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.ui.base.BaseEntityFragment
import com.boltic28.taskmanager.ui.screens.ActivityHelper
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment.Companion.TASK_ID
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_task.*
import java.time.format.DateTimeFormatter

class TaskFragment : BaseEntityFragment<TaskFragmentModel>(R.layout.fragment_task, TASK_ID) {

    override fun initView() {
        setButtonsBack()
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
                    task_fr_close_date_content.text =
                        task.dateClose.format(
                            DateTimeFormatter
                                .ofPattern(resources.getString(R.string.dateFormatterForItems))
                        )
                    task_fr_description_content.text = task.description
                }
            }
    }

    private fun setButtonsBack(){
        task_fr_button_back.setOnClickListener {
            activity?.onBackPressed()
        }
        task_fr_button_home.setOnClickListener {
            findNavController().navigate(R.id.mainFragment)
        }
    }

    private fun checkState(isStarted: Boolean) {
        if (isStarted) {
            task_fr_status.text = resources.getString(R.string.status_in_progress)
            task_fr_button_action.setImageResource(R.drawable.ic_done)
            task_fr_button_action.setOnClickListener {
                //TODO
            }
        } else {
            task_fr_status.text = resources.getString(R.string.status_not_started)
            task_fr_button_action.setImageResource(R.drawable.ic_start)
            task_fr_button_action.setOnClickListener {
                //TODO
            }
        }
    }
}