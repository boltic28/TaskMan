package com.boltic28.taskmanager.ui.screens.activity

import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.navigation.findNavController
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainActivityModel>(R.layout.activity_main), ActivityHelper {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sign_out_toolbar -> {
                model.userManager.signOut()
                setToolbarText(resources.getString(R.string.app_name))
                findNavController(R.id.container).navigate(R.id.signFragment)
                return true
            }
        }
        return false
    }

    override fun setToolbarText(text: String) {
        showToolbar()
        title = text
    }

    override fun hideToolbar(){
        toolbar.visibility = View.GONE
    }

    override fun showToolbar(){
        toolbar.visibility = View.VISIBLE
    }
}