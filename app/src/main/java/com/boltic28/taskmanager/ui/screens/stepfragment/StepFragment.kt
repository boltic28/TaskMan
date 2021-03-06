package com.boltic28.taskmanager.ui.screens.stepfragment

import androidx.navigation.fragment.findNavController
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.ui.base.BaseEntityFragment
import com.boltic28.taskmanager.ui.constant.NO_ID
import com.boltic28.taskmanager.ui.constant.STEP_EXTRA
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_block_buttons.*
import kotlinx.android.synthetic.main.fragment_block_head.*
import kotlinx.android.synthetic.main.fragment_block_idea_convertor.*
import kotlinx.android.synthetic.main.fragment_block_info.*
import kotlinx.android.synthetic.main.fragment_block_recycler.*

class StepFragment : BaseEntityFragment<StepFragmentModel>() {

    override fun initView() {
        setButtonsBack(STEP_EXTRA)
        model.disposables + model.item
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ item ->
                if (item.id != NO_ID) {
                    fillBaseDate(item)
                    attachAdapter(model.adapter)

                    fillStatusData(item.isStarted, item.isDone, item.dateClose)
                    setProgress(item.progress)

                    loadDataIntoRecycler(item)
                    setButtonOwner(item)

                    item_fr_button_action.setOnClickListener {
                        loadDataIntoRecycler(item)
                    }
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

    private fun setButtonOwner(item: Step) {
        activateRelativeLine()
        if (item.goalId != NO_ID) {
            item_fr_relative_button.setImageResource(R.drawable.ic_unlink)
            item_fr_relative_button.setOnClickListener {
                model.update(item.copy(goalId = NO_ID))
            }
            model.disposables + model.getParentName(item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{name ->
                    item_fr_relative_owner.text = name
                }
        } else {
            item_fr_relative_button.setImageResource(R.drawable.ic_link)
            item_fr_relative_owner.text = resources.getString(R.string.not_attached)
            item_fr_relative_button.setOnClickListener {
                item_fr_its_elements.text = resources.getString(R.string.attach_to)
                model.isLoadFreeElements = false
                model.loadGoalsIntoAdapter(item, findNavController())
            }
        }
    }

    private fun loadDataIntoRecycler(item: Step) {
        if (model.isLoadFreeElements) {
            model.isLoadFreeElements = false
            model.loadFreeElementIntoAdapter(findNavController())
            item_fr_its_elements.text = resources.getString(R.string.free_elements)
            item_fr_button_action.setImageResource(R.drawable.ic_elements)
        } else {
            model.isLoadFreeElements = true
            model.loadStepsElementIntoAdapter(item, findNavController())
            item_fr_its_elements.text = resources.getString(R.string.its_elements)
            item_fr_button_action.setImageResource(R.drawable.ic_add_item)
        }
    }
}