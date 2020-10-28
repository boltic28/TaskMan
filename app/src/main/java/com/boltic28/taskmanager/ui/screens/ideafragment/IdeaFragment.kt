package com.boltic28.taskmanager.ui.screens.ideafragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.signtools.FireUserManager
import com.boltic28.taskmanager.ui.screens.ActivityHelper
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_idea.*
import java.time.format.DateTimeFormatter

class IdeaFragment : Fragment(R.layout.fragment_idea) {

    private val model: IdeaFragmentModel by lazy {
        ViewModelProviders.of(this).get(IdeaFragmentModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.userManager = FireUserManager.getInstance(requireActivity())

        val ideaId: Long? = arguments?.getLong(MainFragment.IDEA_ID)

        if (!model.userManager.isUserSigned()) {
            (activity as? ActivityHelper)?.setToolbarText(resources.getString(R.string.app_name))
            findNavController().navigate(R.id.signFragment)
        }

        if (ideaId != null) {
            model.taskId = ideaId
            model.refresh()
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
        model.disposables + model.idea
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

    private fun checkAttachment(idea: Idea) {
        if (idea.goalId == 0L && idea.keyId == 0L && idea.stepId == 0L) {
            idea_fr_is_attached.text = resources.getString(R.string.not_attached)
            idea_fr_owner_button.setImageResource(R.drawable.ic_link)
            idea_fr_owner_button.setOnClickListener {
                idea_fr_recycler.visibility = View.VISIBLE
                idea_fr_name_of_list_in_recycler.visibility = View.VISIBLE
                idea_fr_name_of_list_in_recycler.text = resources.getString(R.string.attach_to)
                idea_fr_recycler.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                model.loadAllElements(idea, findNavController())
            }
        } else {
            idea_fr_recycler.visibility = View.INVISIBLE
            idea_fr_name_of_list_in_recycler.visibility = View.INVISIBLE
            idea_fr_is_attached.text = resources.getString(R.string.attached)
            idea_fr_owner_button.setImageResource(R.drawable.ic_unlink)
            idea_fr_owner_button.setOnClickListener {
                model.update(idea.copy(goalId = 0L, keyId = 0L, stepId = 0L))
            }
        }
    }
}