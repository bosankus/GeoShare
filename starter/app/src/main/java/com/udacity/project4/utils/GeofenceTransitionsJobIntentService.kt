package com.udacity.project4.utils

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import com.udacity.project4.data.dto.Result
import com.udacity.project4.data.local.RemindersLocalRepository
import com.udacity.project4.data.model.Reminder
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

/** This service class is used implemented to handle long running handle task.
 *  In this case, the class handles getting [Reminder] object from
 *  [GeofenceBroadcastReceiver] class and triggers the notification on the basis of
 *  Geofence Transition events
 * */

class GeofenceTransitionsJobIntentService : JobIntentService(), CoroutineScope {

    private var coroutineJob: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + coroutineJob

    companion object {
        private const val JOB_ID = 573
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(
                context,
                GeofenceTransitionsJobIntentService::class.java, JOB_ID,
                intent
            )
        }
    }

    override fun onHandleWork(intent: Intent) {
        val reminderId = intent.getStringExtra("REMINDER_ID")
        Timber.i("received JobServiceIntent: $reminderId")
        reminderId?.let { sendNotification(it) }
    }

    private fun sendNotification(reminderId: String) {
        val remindersLocalRepository: RemindersLocalRepository by inject()
        CoroutineScope(coroutineContext).launch(SupervisorJob()) {
            val result = remindersLocalRepository.getReminder(reminderId)

            if (result is Result.Success<Reminder>) {
                val reminderDTO = result.data
                sendNotifications(
                    this@GeofenceTransitionsJobIntentService, Reminder(
                        reminderDTO.title,
                        reminderDTO.description,
                        reminderDTO.location,
                        reminderDTO.latitude,
                        reminderDTO.longitude,
                        reminderDTO.id
                    )
                )
            }
        }
    }
}