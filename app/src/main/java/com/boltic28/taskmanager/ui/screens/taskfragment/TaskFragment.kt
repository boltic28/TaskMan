package com.boltic28.taskmanager.ui.screens.taskfragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.signtools.FireUserManager
import com.boltic28.taskmanager.ui.screens.ActivityHelper
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_step.*
import kotlinx.android.synthetic.main.fragment_step.step_fr_add_action
import kotlinx.android.synthetic.main.fragment_task.*
import kotlinx.android.synthetic.main.item_task.*
import java.time.format.DateTimeFormatter

class TaskFragment : Fragment(R.layout.fragment_task) {

    private val model: TaskFragmentModel by lazy {
        ViewModelProviders.of(this).get(TaskFragmentModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.userManager = FireUserManager.getInstance(requireActivity())

        val taskId: Long? = arguments?.getLong(MainFragment.TASK_ID)

        if (!model.userManager.isUserSigned()) {
            (activity as? ActivityHelper)?.setToolbarText(resources.getString(R.string.app_name))
            findNavController().navigate(R.id.signFragment)
        }

        if (taskId != null) {
            model.taskId = taskId
            model.refresh()
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
        model.disposables + model.step
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