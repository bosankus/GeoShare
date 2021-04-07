package com.udacity.project4.utils

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

/**Created by
Author: Ankush Bose
Date: 05,April,2021
 **/

const val REMINDER_DATABASE_NAME = "reminder_db"

const val ANDROID_10 = Build.VERSION_CODES.Q
const val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
const val COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
const val BACKGROUND_LOCATION = Manifest.permission.ACCESS_BACKGROUND_LOCATION

const val REQUEST_TURN_DEVICE_LOCATION_ON = 1001
const val ACTION_GEOFENCE_EVENT = "geofence_event"
const val GEOFENCE_RADIUS = 500.0f