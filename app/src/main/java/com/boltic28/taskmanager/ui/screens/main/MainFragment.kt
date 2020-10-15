package com.boltic28.taskmanager.ui.screens.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.di.AppDagger
import com.boltic28.taskmanager.ui.screens.ActivityHelper
import com.boltic28.taskmanager.signtools.FireUserManager
import com.boltic28.taskmanager.utils.Messenger
import io.reactivex.disposables.Disposables
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        const val TAG = "mainActivity_test"
    }

    @Inject
    lateinit var messenger: Messenger

    private var disposable = Disposables.disposed()

    private val model: MainFragmentModel by lazy {
        ViewModelProviders.of(this).get(
            MainFragmentModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppDagger.component.injectFragment(this)
    }

    override fun onStop() {
        super.onStop()
        disposable.dispose()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "-> mainFragment")

        initRecycler()
        model.userManager = FireUserManager.getInstance(requireActivity())
        setOnButtons()

        disposable = model.userManager.user
            .subscribe({ user ->
                if (user.id.isEmpty()) {
                    Log.d(TAG, "user empty")
                    (activity as? ActivityHelper)?.setToolbarText(resources.getString(R.string.app_name))
                    findNavController().navigate(R.id.signFragment)
                } else {
                    (activity as? ActivityHelper)?.setToolbarText(user.email)
                    model.disposable = model.checkGoals()
                }
            }, {
                Log.d(TAG, it.toString())
            })
    }

    private fun initRecycler() {
        main_recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        main_recycler.adapter = model.adapter
    }

    private fun setOnButtons() {
        main_add_button.setOnClickListener {
            findNavController().navigate(R.id.creatorFragment)
        }
    }
}