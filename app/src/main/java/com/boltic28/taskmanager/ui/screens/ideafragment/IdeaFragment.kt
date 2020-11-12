package com.boltic28.taskmanager.ui.screens.ideafragment

import android.app.DatePickerDialog
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.ui.base.BaseEntityFragment
import com.boltic28.taskmanager.ui.constant.IDEA_CONVERTER
import com.boltic28.taskmanager.ui.constant.NO_ID
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_block_buttons.*
import kotlinx.android.synthetic.main.fragment_block_idea_convertor.*
import kotlinx.android.synthetic.main.fragment_block_info.*
import kotlinx.android.synthetic.main.fragment_block_recycler.*
import kotlinx.android.synthetic.main.fragment_item.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

    private fun setCloseDate(closeDate: LocalDate) {
        converter_close_date_value.text = closeDate.format(
            DateTimeFormatter
                .ofPattern(resources.getString(R.string.dateFormatterForCloseDate))
        )
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
        activateIdeaConverter()
        fillConverterData(item)
    }

    private var closeDate = LocalDate.now()
    private var cycleType = ""
    private fun fillConverterData(item: Idea){
        converter_name_value.setText(item.name)
        converter_description_value.setText(item.description)
        converter_cycle_spinner.isEnabled = false
        converter_cycle_spinner.adapter = ArrayAdapter.createFromResource(
            requireView().context,
            R.array.cycle,
            R.layout.support_simple_spinner_dropdown_item
        )

        closeDate = LocalDate.of(item.date.year, item.date.month, item.date.dayOfMonth)
        setCloseDate(closeDate)

        converter_close_date_value.setOnClickListener {
            val listener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                closeDate = LocalDate.of(year, month + 1, dayOfMonth)
                setCloseDate(closeDate)
            }
            val timePicker = DatePickerDialog(
                requireContext(),
                listener,
                closeDate.year,
                closeDate.monthValue - 1,
                closeDate.dayOfMonth
            )
            timePicker.show()
        }

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
                        model.messenger.showMessage("idea converted")
                        model.delete(item)
                        findNavController().navigate(R.id.mainFragment)
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
                closeDate,
                cycleType
            )
            R.id.converter_step_radio -> model.convertToStep(
                item,
                converter_name_value.text.toString(),
                converter_description_value.text.toString(),
                closeDate
            )
            R.id.converter_goal_radio -> model.convertToGoal(
                item,
                converter_name_value.text.toString(),
                converter_description_value.text.toString(),
                closeDate
            )
            else -> Single.just(NO_ID)
        }
}