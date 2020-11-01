package com.boltic28.taskmanager.ui.screens.creator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.di.App
import com.boltic28.taskmanager.ui.screens.ActivityHelper
import com.boltic28.taskmanager.ui.screens.MainActivity
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment
import com.boltic28.taskmanager.utils.Messenger
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_creator.*
import java.time.LocalDateTime
import javax.inject.Inject

class CreatorFragment : Fragment(R.layout.fragment_creator) {

    @Inject
    lateinit var messenger: Messenger

    private var cycleType: String = ""
    private var disposable: Disposable = Disposables.disposed()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.injectFragment(this)
    }

    private val model: CreatorFragmentModel by lazy {
        ViewModelProviders.of(this).get(
            CreatorFragmentModel::class.java
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(MainFragment.TAG, "-> creatorFragment")

        (activity as? ActivityHelper)?.setToolbarText("Create new object")
        setOnButtons()
        setLayout()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.dispose()
    }

    private fun setOnButtons() {
        creator_button_create.setOnClickListener {
            disposable = createItem()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { id ->
                    if (id == 0L){
                        Log.d(MainActivity.TAG, "creating item is FAILED")
                        messenger.showMessage("new instance is not created")
                    }else{
                        Log.d(MainActivity.TAG, "creating item is SUCCESS: id = $id")
                        messenger.showMessage("new instance is created")
                        findNavController().navigate(R.id.mainFragment)
                    }
                }
        }
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

    private fun createItem(): Single<Long> =
        when (creator_group.checkedRadioButtonId) {
            R.id.creator_idea_radio -> model.saveIdea(
                creator_name.text.toString(),
                creator_description.text.toString()
            )
            R.id.creator_task_radio -> model.saveTask(
                creator_name.text.toString(),
                creator_description.text.toString(), LocalDateTime.now(), cycleType
            )
            R.id.creator_step_radio -> model.saveStep(
                creator_name.text.toString(),
                creator_description.text.toString(), LocalDateTime.now()
            )
            R.id.creator_key_radio -> model.saveKey(
                creator_name.text.toString(),
                creator_description.text.toString()
            )
            R.id.creator_goal_radio -> model.saveGoal(
                creator_name.text.toString(),
                creator_description.text.toString(), LocalDateTime.now()
            )
            else -> Single.just(0L)
        }
}