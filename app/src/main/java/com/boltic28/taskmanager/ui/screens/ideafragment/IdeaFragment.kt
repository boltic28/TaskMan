package com.boltic28.taskmanager.ui.screens.ideafragment

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.ui.base.BaseEntityFragment
import com.boltic28.taskmanager.ui.constant.NO_ID
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_block_buttons.*
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
        item_fr_button_action.setImageResource(R.drawable.ic_transform)
        item_fr_button_action.setOnClickListener {
            // todo transform operation
        }
    }
}