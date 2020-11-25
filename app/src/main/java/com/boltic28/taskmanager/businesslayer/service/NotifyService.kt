package com.boltic28.taskmanager.businesslayer.service

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.businesslayer.interactors.ServiceInteractor
import com.boltic28.taskmanager.di.App
import com.boltic28.taskmanager.ui.screens.activity.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import java.time.LocalDateTime
import javax.inject.Inject

class NotifyService : Service() {

    @Inject
    lateinit var interactor: ServiceInteractor

    companion object {
        const val CHANNEL_ID = "TM01"
        const val CHANNEL_NAME = "taskManager"
        const val CHANNEL_DESC = "manage list of the tasks"
        const val NOTIFICATION_ID = 91113

        const val ACTION_CHECK_TASKS = "check task for to day"

        const val ONE_DAY = 86_400_000
    }

    private var disposable = Disposables.disposed()

    private val alarmManager: AlarmManager by lazy {
        getSystemService(AlarmManager::class.java)
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        (application as App).applicationComponent.inject(this)
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        getInfoAboutTaskForToday()
        setupAlarmManager()
        return START_REDELIVER_INTENT
    }

    private fun getInfoAboutTaskForToday() {
        var counter = 0
        disposable = interactor.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { tasks ->
                tasks.forEach {
                    if (it.dateClose.isAfter(LocalDateTime.now().plusDays(1))) counter++
                }
                createNotification(resources.getString(R.string.you_have_do, counter))
            }
    }

    private fun setupAlarmManager() {
        val intent = Intent(this, NotifyService::class.java)
        intent.action = ACTION_CHECK_TASKS

        val pIntent = PendingIntent.getService(this, 0, intent, 0)
        val alarmTime: Long = System.currentTimeMillis() + ONE_DAY

        alarmManager.set(AlarmManager.RTC, alarmTime, pIntent)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = CHANNEL_DESC

            val manager = getSystemService(NotificationManager::class.java)
            manager?.let { manager.createNotificationChannel(channel) }
        }
    }

    private fun createNotification(msg: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setContentTitle(resources.getString(R.string.app_name))
            setContentText(msg)
            setSmallIcon(R.drawable.ic_done)
            setContentIntent(pendingIntent)
            priority = NotificationCompat.PRIORITY_MAX
            setAutoCancel(true)
        }

        NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, builder.build())
    }
}