package com.boltic28.taskmanager.ui.screens.stepfragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.signtools.FireUserManager
import com.boltic28.taskmanager.ui.screens.ActivityHelper
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_step.*
import java.time.format.DateTimeFormatter

class StepFragment : Fragment(R.layout.fragment_step) {

    private val model: StepFragmentModel by lazy {
        ViewModelProviders.of(this).get(StepFragmentModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.userManager = FireUserManager.getInstance(requireActivity())

        val stepId: Long? = arguments?.getLong(MainFragment.STEP_ID)

        if (!model.userManager.isUserSigned()) {
            (activity as? ActivityHelper)?.setToolbarText(resources.getString(R.string.app_name))
            findNavController().navigate(R.id.signFragment)
        }

        if (stepId != null) {
            model.stepId = stepId
            model.refreshStep()
        } else {
            findNavController().navigate(R.id.mainFragment)
        }
        initView()
    }

    override fun onStop() {
        super.onStop()
        model.disposables.forEach { it.dispose() }
    }

    private fun initView() {
        model.disposables + model.step
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
                        model.refreshStep()
                        model.isStepsElementIntoRecycler = false
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
                model.isStepsElementIntoRecycler = false
                model.loadGoalsIntoAdapter(step, findNavController())
            }
        }
    }

    private fun loadDataIntoRecycler(step: Step) {
        if (model.isStepsElementIntoRecycler) {
            model.isStepsElementIntoRecycler = false
            model.loadFreeElementIntoAdapter(findNavController())
            step_fr_add_action.text = resources.getString(R.string.to_steps_element)
        } else {
            model.isStepsElementIntoRecycler = true
            model.loadStepsElementIntoAdapter(step, findNavController())
            step_fr_add_action.text = resources.getString(R.string.to_free_elements)
        }
    }
}