package com.boltic28.taskmanager.businesslayer.syncmanager

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.boltic28.taskmanager.businesslayer.usecases.RefreshDataUseCase
import com.boltic28.taskmanager.di.App
import javax.inject.Inject

class SyncService : Service() {

    @Inject
    lateinit var interactor: RefreshDataUseCase

    companion object {
        private var sSyncAdapter: SyncAdapter? = null
        private val sSyncAdapterLock = Any()
    }

    override fun onCreate() {
        super.onCreate()
        (application as App).applicationComponent.inject(this)

        synchronized(sSyncAdapterLock) {
            sSyncAdapter = sSyncAdapter ?: SyncAdapter(applicationContext, interactor)
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return sSyncAdapter?.syncAdapterBinder ?: throw IllegalStateException()
    }

}