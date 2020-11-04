package com.boltic28.taskmanager.ui.screens

import android.view.Menu
import android.view.MenuItem
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.ui.base.BaseActivity

class MainActivity : BaseActivity<MainActivityModel>(R.layout.activity_main), ActivityHelper{

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
        title = text
    }

}