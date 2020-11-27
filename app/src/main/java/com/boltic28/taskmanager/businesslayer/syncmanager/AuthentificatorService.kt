package com.boltic28.taskmanager.businesslayer.syncmanager

import android.app.Service
import android.content.Intent
import android.os.IBinder

class AuthentificatorService: Service() {

    private lateinit var mAuthenticator: Authenticator

    override fun onCreate() {
        mAuthenticator = Authenticator(this)
    }

    override fun onBind(intent: Intent?): IBinder = mAuthenticator.iBinder
}