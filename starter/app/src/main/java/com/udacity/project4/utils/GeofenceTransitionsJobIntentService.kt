package com.udacity.project4.utils

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import com.google.android.gms.location.Geofence
import com.udacity.project4.data.dto.Result
import com.udacity.project4.data.local.RemindersLocalRepository
import com.udacity.project4.data.model.Reminder
import com.udacity.project4.view.reminderslist.ReminderDataItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class GeofenceTransitionsJobIntentService : JobIntentService(), CoroutineScope {

    @Inject
    lateinit var remindersLocalRepository: RemindersLocalRepository

    private var coroutineJob: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + coroutineJob

    companion object {
        private const val JOB_ID = 573

        //        TODO: call this to start the JobIntentService to handle the geofencing transition events
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(
                context,
                GeofenceTransitionsJobIntentService::class.java, JOB_ID,
                intent
            )
        }
    }

    override fun onHandleWork(intent: Intent) {
        //TODO: handle the geofencing transition events and
        // send a notification to the user when he enters the geofence area
        //TODO call @sendNotification
    }

    //TODO: get the request id of the current geofence
    private fun sendNotification(triggeringGeofences: List<Geofence>) {
        val requestId = ""

//        Interaction to the repository has to be through a coroutine scope
        CoroutineScope(coroutineContext).launch(SupervisorJob()) {
            //get the reminder with the request id
            val result = remindersLocalRepository.getReminder(requestId)
            if (result is Result.Success<Reminder>) {
                val reminderDTO = result.data
                //send a notification to the user with the reminder details
                sendNotification(
                    this@GeofenceTransitionsJobIntentService, ReminderDataItem(
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