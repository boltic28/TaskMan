package com.boltic28.taskmanager.ui.screens.keyfragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.signtools.FireUserManager
import com.boltic28.taskmanager.ui.screens.ActivityHelper
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_key.*
import java.time.format.DateTimeFormatter

class KeyFragment: Fragment(R.layout.fragment_key) {

    private val model: KeyFragmentModel by lazy {
        ViewModelProviders.of(this).get(KeyFragmentModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.userManager = FireUserManager.getInstance(requireActivity())

        val keyId: Long? = arguments?.getLong(MainFragment.KEY_ID)

        if (!model.userManager.isUserSigned()) {
            (activity as? ActivityHelper)?.setToolbarText(resources.getString(R.string.app_name))
            findNavController().navigate(R.id.signFragment)
        }

        if (keyId != null) {
            model.keyId = keyId
            model.refreshKey()
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
        model.disposables + model.key
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
                        model.refreshKey()
                        model.isKeysElementIntoRecycler = false
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
                model.isKeysElementIntoRecycler = false
                model.loadGoalsIntoAdapter(key, findNavController())
            }
        }
    }

    private fun loadDataIntoRecycler(key: KeyResult) {
        if (model.isKeysElementIntoRecycler) {
            model.isKeysElementIntoRecycler = false
            model.loadFreeElementIntoAdapter(findNavController())
            key_fr_add_action.text = resources.getString(R.string.to_steps_element)
        } else {
            model.isKeysElementIntoRecycler = true
            model.loadKeysElementIntoAdapter(key, findNavController())
            key_fr_add_action.text = resources.getString(R.string.to_free_elements)
        }
    }
}