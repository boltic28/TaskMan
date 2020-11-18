package com.boltic28.taskmanager.ui.screens.creator

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.ui.base.BaseFragment
import com.boltic28.taskmanager.ui.constant.*
import com.boltic28.taskmanager.ui.screens.activity.ActivityHelper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_creator.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class CreatorFragment : BaseFragment<CreatorFragmentModel>(R.layout.fragment_creator) {

    private var cycleType: String = ""
    private var closeDate = LocalDate.now().plusDays(1)
    private var disposable: Disposable = Disposables.disposed()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? ActivityHelper)?.setToolbarText("Create new object")
        setOnButtons()
        setLayout()
        setCloseDate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.dispose()
    }

    private fun setOnButtons() {
        creator_button_create.setOnClickListener {
            if (creator_name.text.toString().isNotEmpty()) {
                disposable = createItem()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { id ->
                        if (id == NO_ID) {
                            model.messenger.showMessage("new instance is not created")
                        } else {
                            model.messenger.showMessage("new instance is created")
                            findNavController().navigate(R.id.mainFragment, bundle)
                        }
                    }
            }else{
                model.messenger.showMessage(resources.getString(R.string.field_name_must_be_not_empty))
            }
        }
    }

    private fun setCloseDate() {
        creator_close_date_value.text = closeDate.format(
            DateTimeFormatter
                .ofPattern(resources.getString(R.string.dateFormatterForCloseDate))
        )
    }

    private fun setLayout() {
        creator_cycle_spinner.isEnabled = false
        creator_task_radio.setOnCheckedChangeListener { _, _ ->
            if (creator_task_radio.isChecked) {
                creator_is_cycle_checkbox.isEnabled = true
            } else {
                creator_is_cycle_checkbox.isEnabled = false
                creator_cycle_spinner.isEnabled = false
            }
        }
        creator_idea_radio.setOnCheckedChangeListener { _, _ ->
            if (creator_idea_radio.isChecked) {
                creator_close_date_value.isEnabled = false
                creator_close_date.isEnabled = false
            } else {
                creator_close_date_value.isEnabled = true
                creator_close_date.isEnabled = true
                setCloseDateClockListener()
            }
        }
        creator_is_cycle_checkbox.setOnCheckedChangeListener { _, _ ->
            creator_cycle_spinner.isEnabled = creator_is_cycle_checkbox.isChecked
        }
        creator_cycle_spinner.adapter = ArrayAdapter.createFromResource(
            requireView().context,
            R.array.cycle,
            R.layout.support_simple_spinner_dropdown_item
        )
        creator_cycle_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                cycleType = resources.getStringArray(R.array.cycle)[position]
            }
        }
    }

    private fun setCloseDateClockListener() {
        creator_close_date_value.setOnClickListener {
            val listener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                closeDate = LocalDate.of(year, month + 1, dayOfMonth)
                setCloseDate()
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
    }

    private val bundle = Bundle()
    private fun createItem(): Single<Long> =
        when (creator_group.checkedRadioButtonId) {
            R.id.creator_idea_radio -> model.saveIdea(
                creator_name.text.toString(),
                creator_description.text.toString()
            ).doOnSuccess { bundle.putString(LOAD_LIST, IDEA_EXTRA) }
            R.id.creator_task_radio -> model.saveTask(
                creator_name.text.toString(),
                creator_description.text.toString(),
                LocalDateTime.of(closeDate, LocalTime.now()),
                cycleType
            ).doOnSuccess { bundle.putString(LOAD_LIST, TASK_EXTRA) }
            R.id.creator_step_radio -> model.saveStep(
                creator_name.text.toString(),
                creator_description.text.toString(),
                LocalDateTime.of(closeDate, LocalTime.now())
            ).doOnSuccess { bundle.putString(LOAD_LIST, STEP_EXTRA) }
            R.id.creator_key_radio -> model.saveKey(
                creator_name.text.toString(),
                creator_description.text.toString(),
                LocalDateTime.of(closeDate, LocalTime.now())
            ).doOnSuccess { bundle.putString(LOAD_LIST, KEY_EXTRA) }
            R.id.creator_goal_radio -> model.saveGoal(
                creator_name.text.toString(),
                creator_description.text.toString(),
                LocalDateTime.of(closeDate, LocalTime.now())
            ).doOnSuccess { bundle.putString(LOAD_LIST, GOAL_EXTRA) }
            else -> Single.just(NO_ID)
        }
}