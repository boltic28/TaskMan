package com.boltic28.taskmanager.ui.screens.activity

import android.content.Context
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
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
            R.id.settings_toolbar -> {
                findNavController(R.id.container).navigate(R.id.settingsFragment)
                return true
            }
        }
        return false
    }

    override fun setToolbarText(text: String) {
        showToolbar()
        title = text
    }

    override fun hideToolbar() {
        toolbar.visibility = View.GONE
    }

    override fun showToolbar() {
        toolbar.visibility = View.VISIBLE
    }

    override fun hideKeyBoard() {
        currentFocus?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    override fun showProgressBar() {
        main_progressbar.visibility = View.VISIBLE
        main_progressbar_text.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        main_progressbar.visibility = View.INVISIBLE
        main_progressbar_text.visibility = View.INVISIBLE
    }
}