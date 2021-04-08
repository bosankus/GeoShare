package com.udacity.project4.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import timber.log.Timber

/**
 * Triggered by the Geofence.  Since we can have many Geofences at once, we pull the request
 * ID from the first Geofence, and locate it within the cached data in our Room DB
 *
 * Or users can add the reminders and then close the app, So our app has to run in the background
 * and handle the geofencing in the background.
 * To do that you can use https://developer.android.com/reference/android/support/v4/app/JobIntentService to do that.
 *
 */

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION_GEOFENCE_EVENT) {

            val geoFencingEvent = GeofencingEvent.fromIntent(intent)

            if (geoFencingEvent.hasError()) {
                Timber.i("Error while activating Geofencing: ${geoFencingEvent.errorCode}")
                return
            } else {

                val geoFenceTransition = geoFencingEvent.geofenceTransition

                if (geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
                    val geofenceId = geoFencingEvent.triggeringGeofences[0].requestId

                    val jobIntent = Intent(context, GeofenceTransitionsJobIntentService::class.java)
                        .putExtra("REMINDER_ID", geofenceId)
                    GeofenceTransitionsJobIntentService.enqueueWork(context, jobIntent)
                }
            }
        }
    }
}