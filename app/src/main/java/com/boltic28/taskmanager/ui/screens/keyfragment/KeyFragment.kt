package com.boltic28.taskmanager.ui.screens.keyfragment

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.ui.base.BaseEntityFragment
import com.boltic28.taskmanager.ui.screens.ActivityHelper
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment.Companion.KEY_ID
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_key.*

class KeyFragment: BaseEntityFragment<KeyFragmentModel>(R.layout.fragment_key, KEY_ID) {

    override fun initView() {
        setButtonsBack()
        model.disposables + model.item
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ key ->
                if (key.id != 0L) {
                    (activity as? ActivityHelper)?.setToolbarText(key.name)
                    key_fr_name.text = fetchName(key.name)
                    key_fr_image.setImageResource(R.drawable.key_ph)
                    key_fr_start_date.text = fetchDate(key.date)
                    key_fr_description_content.text = key.description
                    key_fr_recycler.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    key_fr_recycler.adapter = model.adapter
                    key_fr_button_action.setOnClickListener { loadDataIntoRecycler(key) }

                    setProgress(key.progress)
                    setButtonOwner(key)
                    loadDataIntoRecycler(key)
                }
            }, {

            })
    }

    private fun setProgress(progress: Progress) {
        key_fr_progress.text = resources.getString(R.string.progres_00, 0)
        if (progress.value >= Progress.PROGRESS_20.value) {
            key_fr_progress_20.setImageResource(R.drawable.bg_progress_on)
            key_fr_progress.text = resources.getString(R.string.progres_00, 20)
        }
        if (progress.value >= Progress.PROGRESS_40.value) {
            key_fr_progress_40.setImageResource(R.drawable.bg_progress_on)
            key_fr_progress.text = resources.getString(R.string.progres_00, 40)
        }
        if (progress.value >= Progress.PROGRESS_60.value) {
            key_fr_progress_60.setImageResource(R.drawable.bg_progress_on)
            key_fr_progress.text = resources.getString(R.string.progres_00, 60)
        }
        if (progress.value >= Progress.PROGRESS_80.value) {
            key_fr_progress_80.setImageResource(R.drawable.bg_progress_on)
            key_fr_progress.text = resources.getString(R.string.progres_00, 80)
        }
        if (progress.value == Progress.DONE.value) {
            key_fr_progress_100.setImageResource(R.drawable.bg_progress_on)
            key_fr_progress.text = resources.getString(R.string.progres_00, 100)
        }
    }

    private fun setButtonsBack(){
        key_fr_button_back.setOnClickListener {
            activity?.onBackPressed()
        }
        key_fr_button_home.setOnClickListener {
            findNavController().navigate(R.id.mainFragment)
        }
    }

    private fun setButtonOwner(key: KeyResult){
        if (key.goalId != 0L){
            key_fr_owner_button.setImageResource(R.drawable.ic_unlink)
            key_fr_owner_button.setOnClickListener {
                model.update(key.copy(goalId = 0L))
            }
            model.disposables + model.interactor.getGoalById(key.goalId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( {
                    key_fr_relative_owner.text = it.name
                },{

                })
        }else{
            key_fr_relative_owner.text = resources.getString(R.string.not_attached)
            key_fr_owner_button.setImageResource(R.drawable.ic_link)
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
            key_fr_its_elements.text = resources.getString(R.string.free_elements)
            key_fr_button_action.setImageResource(R.drawable.ic_elements)
        } else {
            model.isItemsElementIntoRecycler = true
            model.loadKeysElementIntoAdapter(key, findNavController())
            key_fr_its_elements.text = resources.getString(R.string.its_elements)
            key_fr_button_action.setImageResource(R.drawable.ic_add_item)
        }
    }
}