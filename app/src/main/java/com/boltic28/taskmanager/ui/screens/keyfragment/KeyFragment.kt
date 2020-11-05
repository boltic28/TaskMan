package com.boltic28.taskmanager.ui.screens.keyfragment

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.ui.base.BaseEntityFragment
import com.boltic28.taskmanager.ui.screens.activity.ActivityHelper
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment.Companion.KEY_ID
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_key.*
import java.time.format.DateTimeFormatter

class KeyFragment: BaseEntityFragment<KeyFragmentModel>(R.layout.fragment_key, KEY_ID) {

    override fun initView() {
        model.disposables + model.item
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ key ->
                if (key.id != 0L) {
                    (activity as? ActivityHelper)?.setToolbarText(key.name)
                    key_fr_description.text = key.description
                    key_fr_start_date.text =
                        key.date.format(
                            DateTimeFormatter
                                .ofPattern(resources.getString(R.string.dateFormatterForItems))
                        )
                    key_fr_percentage.text =
                        resources.getString(R.string.percentage)
                    key_fr_recycler.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    key_fr_recycler.adapter = model.adapter
                    key_fr_add_action.setOnClickListener {
                        loadDataIntoRecycler(key)
                    }

                    setButtonOwner(key)
                    loadDataIntoRecycler(key)
                }
            }, {

            })
    }

    private fun setButtonOwner(key: KeyResult){
        if (key.goalId != 0L){
            key_fr_owner_button.text = resources.getString(R.string.unpin)
            key_fr_owner_button.setOnClickListener {
                model.interactor.update(key.copy(goalId = 0L))
                    .subscribeOn(Schedulers.io())
                    .subscribe { _ ->
                        model.refresh()
                        model.isItemsElementIntoRecycler = false
                    }
            }
            model.disposables + model.interactor.getGoalById(key.goalId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( {
                    key_fr_owner_text.text = it.name
                },{

                })
        }else{
            key_fr_owner_text.text = resources.getString(R.string.not_attached)
            key_fr_owner_button.text = resources.getString(R.string.attach)
            key_fr_owner_button.setOnClickListener {
                model.isItemsElementIntoRecycler = false
                model.loadGoalsIntoAdapter(key, findNavController())
            }
        }
    }

    private fun loadDataIntoRecycler(key: KeyResult) {
        if (model.isItemsElementIntoRecycler) {
            model.isItemsElementIntoRecycler = false
            model.loadFreeElementIntoAdapter(findNavController())
            key_fr_add_action.text = resources.getString(R.string.to_steps_element)
        } else {
            model.isItemsElementIntoRecycler = true
            model.loadKeysElementIntoAdapter(key, findNavController())
            key_fr_add_action.text = resources.getString(R.string.to_free_elements)
        }
    }
}