package com.boltic28.taskmanager.ui.screens.ideafragment

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.Cycle
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.ui.base.BaseEntityFragment
import com.boltic28.taskmanager.ui.base.setDate
import com.boltic28.taskmanager.ui.constant.IDEA_CONVERTER
import com.boltic28.taskmanager.ui.constant.NO_ID
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_block_buttons.*
import kotlinx.android.synthetic.main.fragment_block_head.*
import kotlinx.android.synthetic.main.fragment_block_idea_convertor.*
import kotlinx.android.synthetic.main.fragment_block_info.*
import kotlinx.android.synthetic.main.fragment_block_recycler.*
import kotlinx.android.synthetic.main.fragment_item.*

class IdeaFragment : BaseEntityFragment<IdeaFragmentModel>() {

    override fun initView() {
        setButtonsBack()
        model.disposables + model.item
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { item ->
                if (item.id != NO_ID) {
                    fillBaseDate(item)
                    attachAdapter(model.adapter)
                    setButtonOwner(item)
                    setActionButton(item)
                    deactivateDataClosing()

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

    private fun setButtonOwner(item: Idea) {
        activateRelativeLine()
        item_recycler_block.visibility = View.INVISIBLE
        if (item.goalId != NO_ID || item.keyId != NO_ID || item.stepId != NO_ID) {
            item_fr_relative_button.setImageResource(R.drawable.ic_unlink)
            item_fr_relative_button.setOnClickListener {
                model.update(item.copy(goalId = NO_ID, keyId = NO_ID, stepId = NO_ID))
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
                item_fr_its_elements.text = resources.getString(R.string.attach_to)
                item_fr_recycler.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                item_fr_recycler.adapter = model.adapter
                model.loadFreeElementsIntoAdapter(item, findNavController())
            }
        }
    }

    private fun setActionButton(item: Idea) {
        this.arguments?.let {
            if (it.getBoolean(IDEA_CONVERTER, false)) initConverter(item)
        }

        item_fr_button_action.setImageResource(R.drawable.ic_transform)
        item_fr_button_action.setOnClickListener {
            initConverter(item)
        }
    }

    private fun initConverter(item: Idea){
        activateConverter()
        fillConverterData(item)
    }

    private fun initSetter(item: Idea){
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


    private var cycleType = Cycle.NOT_CYCLE.value
    private fun fillConverterData(item: Idea){
        converter_name_value.setText(item.name)
        converter_description_value.setText(item.description)
        converter_cycle_spinner.isEnabled = false
        converter_cycle_spinner.adapter = ArrayAdapter.createFromResource(
            requireView().context,
            R.array.cycle,
            R.layout.support_simple_spinner_dropdown_item
        )
        converter_open_date_value.setDate(item.date)
        converter_close_date_value.setDate(item.date)

        converter_task_radio.setOnCheckedChangeListener { _, _ ->
            if (converter_task_radio.isChecked) {
                converter_is_cycle_checkbox.isEnabled = true
            } else {
                converter_is_cycle_checkbox.isEnabled = false
                converter_cycle_spinner.isEnabled = false
            }
        }
        converter_is_cycle_checkbox.setOnCheckedChangeListener { _, _ ->
            converter_cycle_spinner.isEnabled = converter_is_cycle_checkbox.isChecked
        }

        converter_cycle_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {}

                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {
                    cycleType = resources.getStringArray(R.array.cycle)[position]
                }
            }

        converter_button_create.setOnClickListener {
            createItem(item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { id ->
                    if (id == NO_ID) {
                        model.messenger.showMessage("idea is not converted")
                    } else {
                        model.delete(item, findNavController())
                    }
                }
        }
    }

    private fun createItem(item: Idea): Single<Long> =
        when (converter_group.checkedRadioButtonId) {
            R.id.converter_task_radio -> model.convertToTask(
                item,
                converter_name_value.text.toString(),
                converter_description_value.text.toString(),
                openDateTimePicker,
                closeDateTimePicker,
                cycleType
            )
            R.id.converter_step_radio -> model.convertToStep(
                item,
                converter_name_value.text.toString(),
                converter_description_value.text.toString(),
                openDateTimePicker,
                closeDateTimePicker
            )
            R.id.converter_goal_radio -> model.convertToGoal(
                converter_name_value.text.toString(),
                converter_description_value.text.toString(),
                openDateTimePicker,
                closeDateTimePicker
            )
            else -> Single.just(NO_ID)
        }
}