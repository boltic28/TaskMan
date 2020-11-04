package com.boltic28.taskmanager.ui.screens.stepfragment

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.ui.base.BaseEntityFragment
import com.boltic28.taskmanager.ui.screens.activity.ActivityHelper
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment.Companion.STEP_ID
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_step.*
import java.time.format.DateTimeFormatter

class StepFragment : BaseEntityFragment<StepFragmentModel>(R.layout.fragment_step, STEP_ID) {

    override fun initView() {
        model.disposables + model.item
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ step ->
                if (step.id != 0L) {
                    (activity as? ActivityHelper)?.setToolbarText(step.name)
                    step_fr_description.text = step.description
                    step_fr_start_date.text =
                        step.date.format(
                            DateTimeFormatter
                                .ofPattern(resources.getString(R.string.dateFormatterForItems))
                        )
                    step_fr_percentage.text =
                        resources.getString(R.string.percentage)
                    step_fr_recycler.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    step_fr_recycler.adapter = model.adapter
                    step_fr_add_action.setOnClickListener {
                        loadDataIntoRecycler(step)
                    }

                    setButtonOwner(step)
                    loadDataIntoRecycler(step)
                }
            }, {

            })
    }

    private fun setButtonOwner(step: Step){
        if (step.goalId != 0L){
            step_fr_owner_button.text = resources.getString(R.string.unpin)
            step_fr_owner_button.setOnClickListener {
                model.interactor.update(step.copy(goalId = 0L))
                    .subscribeOn(Schedulers.io())
                    .subscribe { _ ->
                        model.refresh()
                        model.isItemsElementIntoRecycler = false
                    }
            }
            model.disposables + model.interactor.getGoalById(step.goalId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( {
                    step_fr_owner_text.text = it.name
                },{

                })
        }else{
            step_fr_owner_text.text = resources.getString(R.string.not_attached)
            step_fr_owner_button.text = resources.getString(R.string.attach)
            step_fr_owner_button.setOnClickListener {
                model.isItemsElementIntoRecycler = false
                model.loadGoalsIntoAdapter(step, findNavController())
            }
        }
    }

    private fun loadDataIntoRecycler(step: Step) {
        if (model.isItemsElementIntoRecycler) {
            model.isItemsElementIntoRecycler = false
            model.loadFreeElementIntoAdapter(findNavController())
            step_fr_add_action.text = resources.getString(R.string.to_steps_element)
        } else {
            model.isItemsElementIntoRecycler = true
            model.loadStepsElementIntoAdapter(step, findNavController())
            step_fr_add_action.text = resources.getString(R.string.to_free_elements)
        }
    }
}