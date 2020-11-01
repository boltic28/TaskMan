package com.boltic28.taskmanager.ui.screens

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.ui.base.BaseActivity
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment

class MainActivity : BaseActivity<MainActivityModel>(R.layout.activity_main), ActivityHelper {

    companion object {
        const val TAG = "mainActivity_test"
    }

    private val model: MainActivityModel by lazy { ViewModelProviders.of(this).get(
        MainActivityModel::class.java)}

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sign_out_toolbar -> {
                model.userManager.signOut()
                return true
            }
        }
        return false
    }

    override fun setToolbarText(text: String) {
        Log.d(MainFragment.TAG,"set toolbar")
        title = text
    }
}