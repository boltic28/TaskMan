package com.boltic28.taskmanager.ui.screens.ideafragment

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.ui.base.BaseEntityFragment
import com.boltic28.taskmanager.ui.screens.ActivityHelper
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment.Companion.IDEA_ID
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_idea.*
import java.time.format.DateTimeFormatter

class IdeaFragment : BaseEntityFragment<IdeaFragmentModel>(R.layout.fragment_idea, IDEA_ID) {

    override fun initView() {
        setButtonsBack()
        model.disposables + model.item
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { idea ->
                if (idea.id != 0L) {
                    (activity as? ActivityHelper)?.setToolbarText(idea.name)
                    idea_fr_name.text = idea.name
                    idea_fr_start_date.text =
                        idea.date.format(
                            DateTimeFormatter
                                .ofPattern(resources.getString(R.string.dateFormatterForItems))
                        )
                    checkAttachment(idea)
                    idea_fr_description_content.text = idea.description
                }
            }
    }

    private fun setButtonsBack(){
        idea_fr_button_back.setOnClickListener {
            activity?.onBackPressed()
        }
        idea_fr_button_home.setOnClickListener {
            findNavController().navigate(R.id.mainFragment)
        }
    }

    private fun checkAttachment(idea: Idea) {
        idea_fr_button_action.setImageResource(R.drawable.ic_transform)
        idea_fr_button_action.setOnClickListener {
            // todo transform operation
        }

        if (idea.goalId == 0L && idea.keyId == 0L && idea.stepId == 0L) {
            idea_fr_relative_owner.text = resources.getString(R.string.not_attached)
            idea_fr_owner_button.setImageResource(R.drawable.ic_link)
            idea_fr_owner_button.setOnClickListener {
                idea_fr_recycler.visibility = View.VISIBLE
                idea_fr_its_elements.visibility = View.VISIBLE
                idea_fr_its_elements.text = resources.getString(R.string.attach_to)
                idea_fr_recycler.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                model.loadAllElements(idea, findNavController())
            }
        } else {
            idea_fr_recycler.visibility = View.INVISIBLE
            idea_fr_its_elements.visibility = View.INVISIBLE
            idea_fr_relative_owner.text = resources.getString(R.string.attached)  //todo get parent name like step
            idea_fr_owner_button.setImageResource(R.drawable.ic_unlink)
            idea_fr_owner_button.setOnClickListener {
                model.update(idea.copy(goalId = 0L, keyId = 0L, stepId = 0L))
            }
        }
    }
}