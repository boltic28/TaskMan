package com.boltic28.taskmanager.ui.screens.goalfragment

import androidx.navigation.fragment.findNavController
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.ui.base.BaseEntityFragment
import com.boltic28.taskmanager.ui.constant.NO_ID
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_block_buttons.*
import kotlinx.android.synthetic.main.fragment_block_head.*
import kotlinx.android.synthetic.main.fragment_block_idea_convertor.*
import kotlinx.android.synthetic.main.fragment_block_recycler.*

class GoalFragment : BaseEntityFragment<GoalFragmentModel>() {

    override fun initView() {
        setButtonsBack()
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

    private fun loadDataIntoRecycler(item: Goal) {
        if (model.isItemsElementIntoRecycler) {
            model.isItemsElementIntoRecycler = false
            model.loadFreeElementIntoAdapter(findNavController())
            item_fr_its_elements.text = resources.getString(R.string.free_elements)
            item_fr_button_action.setImageResource(R.drawable.ic_elements)
        } else {
            model.isItemsElementIntoRecycler = true
            model.loadGoalsElementIntoAdapter(item, findNavController())
            item_fr_its_elements.text = resources.getString(R.string.its_elements)
            item_fr_button_action.setImageResource(R.drawable.ic_add_item)
        }
    }
}