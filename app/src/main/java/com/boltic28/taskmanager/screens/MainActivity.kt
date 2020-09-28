package com.boltic28.taskmanager.screens

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.screens.main.MainFragment
import com.boltic28.taskmanager.screens.sign.SignFragment
import com.boltic28.taskmanager.signtools.FireUserManager

class MainActivity : AppCompatActivity(), ActivityHelper {

    companion object {
        const val TAG = "mainActivity_test"
    }

    private val model: MainActivityModel by lazy { ViewModelProviders.of(this).get(
        MainActivityModel::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(MainFragment.TAG, "-> mainActivity")

        model.userManager = FireUserManager.getInstance(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sign_out -> {
                model.userManager.signOut()
                return true
            }
        }
        return false
    }

    override fun setToolbarText(text: String) {
        title = text
    }
}