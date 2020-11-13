package com.boltic28.taskmanager.ui.screens.taskfragment

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.ui.base.BaseEntityFragment
import com.boltic28.taskmanager.ui.constant.NO_ID
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_block_buttons.*
import kotlinx.android.synthetic.main.fragment_block_head.*
import kotlinx.android.synthetic.main.fragment_block_idea_convertor.*
import kotlinx.android.synthetic.main.fragment_block_info.*
import kotlinx.android.synthetic.main.fragment_block_recycler.*
import kotlinx.android.synthetic.main.fragment_item.*

class TaskFragment : BaseEntityFragment<TaskFragmentModel>() {

    override fun initView() {
        setButtonsBack()
        model.disposables + model.item
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { item ->
                if (item.id != NO_ID) {
                    fillBaseDate(item)
                    attachAdapter(model.adapter)

                    fillStatusData(item.isStarted, item.isDone, item.dateClose)

                    activateCycleLine()
                    item_fr_cycle_content.text = item.cycle.value

                    setButtonOwner(item)
                    checkState(item)

                    item_fr_settings.setOnClickListener {
                        initSetter(item)
                        converter_button_create.setOnClickListener {
                            model.update(item.copy(
                                name = converter_name_value.text.toString(),
                                description = converter_description_value.text.toString(),
                                date = openDateTimePicker,
                                dateClose = closeDateTimePicker
                            ))
                            deactivateSettingsView()
                        }
                    }
                }
            }
    }

    private fun setButtonOwner(item: Task) {
        activateRelativeLine()
        item_recycler_block.visibility = View.INVISIBLE
        if ((item.goalId != NO_ID) || (item.stepId != NO_ID) || (item.keyId != NO_ID)) {
            item_fr_relative_button.setImageResource(R.drawable.ic_unlink)
            item_fr_relative_button.setOnClickListener {
                model.update(item.copy(goalId = NO_ID, stepId = NO_ID, keyId = NO_ID))
            }
            model.disposables + model.getParentName(item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { ownerName ->
                    item_fr_relative_owner.text = ownerName
                }
        } else {
            item_fr_relative_owner.text = resources.getString(R.string.not_attached)
            item_fr_relative_button.setImageResource(R.drawable.ic_link)
            item_fr_relative_button.setOnClickListener {
                item_recycler_block.visibility = View.VISIBLE
                item_fr_its_elements.text = resources.getString(R.string.free_elements)
                item_fr_recycler.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                item_fr_recycler.adapter = model.adapter
                model.loadParentsElements(item, findNavController())
            }
        }
    }

    private fun checkState(task: Task) {
        item_fr_button_action.isActivated = !task.isDone

        if (model.isAttention(task)){
            item_fr_isDone_icon.visibility = View.VISIBLE
            item_fr_isDone_icon.setImageResource(R.drawable.ic_attention)
        }

        if (task.isStarted) {
            item_fr_button_action.setImageResource(R.drawable.ic_done)
            item_fr_button_action.setOnClickListener {
                model.update(task.copy(isDone = true))
            }
        } else {
            item_fr_button_action.setImageResource(R.drawable.ic_start)
            item_fr_button_action.setOnClickListener {
                model.update(task.copy(isStarted = true))
            }
        }
    }

    private fun initSetter(item: Task){
        activateSettingsView(item)
        converter_button_create.setOnClickListener {
            model.update(item.copy(
                name = converter_name_value.text.toString(),
                description = converter_description_value.text.toString(),
                date = openDateTimePicker,
                dateClose = closeDateTimePicker
            ))
            deactivateSettingsView()
        }
        converter_button_delete.setOnClickListener {
            model.delete(item, findNavController())
        }
    }
}