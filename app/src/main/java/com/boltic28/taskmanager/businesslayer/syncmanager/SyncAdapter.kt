package com.boltic28.taskmanager.businesslayer.syncmanager

import android.accounts.Account
import android.content.AbstractThreadedSyncAdapter
import android.content.ContentProviderClient
import android.content.Context
import android.content.SyncResult
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.boltic28.taskmanager.businesslayer.usecases.RefreshDataUseCase
import com.google.firebase.FirebaseApp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SyncAdapter(
    context: Context,
    private val interactor: RefreshDataUseCase
) : RefreshDataUseCase by interactor,
    AbstractThreadedSyncAdapter(context, true, false) {

    override fun onPerformSync(
        p0: Account?,
        p1: Bundle?,
        p2: String?,
        p3: ContentProviderClient?,
        p4: SyncResult?
    ) {
        FirebaseApp.initializeApp(context)

        Log.d("SYNC_DATA", "start updating")
        val disposable = interactor.refreshAllData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("SYNC_DATA", "one more item taken")
            }
        Handler(context.mainLooper).postDelayed({disposable.dispose()}, 5000)
    }
}