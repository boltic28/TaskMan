package com.boltic28.taskmanager.utils

import android.content.Context
import android.widget.Toast

class Messenger(private val context: Context) {

    fun showMessage(msg: String){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

}