package com.udacity.project4.view.main

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.udacity.project4.R
import com.udacity.project4.databinding.ActivityMainBinding
import com.udacity.project4.utils.AuthManager
import com.udacity.project4.utils.REQUEST_TURN_DEVICE_LOCATION_ON
import com.udacity.project4.utils.showSnack
import dagger.hilt.android.AndroidEntryPoint

/**
 * The RemindersActivity that holds the reminders fragments
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var authManager: AuthManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        authManager = AuthManager(this)
    }

    override fun onStart() {
        super.onStart()
        checkDeviceLocationSettings()
    }

    private fun checkDeviceLocationSettings(resolve: Boolean = true) {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_LOW_POWER
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val settingsClient = LocationServices.getSettingsClient(this)
        val locationSettingsResponseTask =
            settingsClient.checkLocationSettings(builder.build())

        locationSettingsResponseTask.addOnFailureListener { exception ->
            if (exception is ResolvableApiException && resolve) {
                try {
                    exception.startResolutionForResult(
                        this, REQUEST_TURN_DEVICE_LOCATION_ON
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    showSnack(
                        binding.activityMain,
                        "Error getting location settings resolution: ${sendEx.message}"
                    )
                }
            } else {
                showSnack(
                    binding.activityMain,
                    resources.getString(R.string.location_required_error)
                )
            }
        }
        locationSettingsResponseTask.addOnCompleteListener {}
    }


    private fun goToReminderFragment() {
        findNavController(R.id.nav_host_fragment)
            .navigate(R.id.action_authenticationFragment_to_reminderListFragment)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            authManager.handleAuth(requestCode, resultCode, data) { isSuccessful, error ->
                if (isSuccessful && authManager.userDetails != null && error == null) {
                    goToReminderFragment()
                }
            }
        } else if (requestCode == REQUEST_TURN_DEVICE_LOCATION_ON)
            showSnack(binding.activityMain, "Background location permission provided")
        else showSnack(binding.activityMain, "Failed to authenticate")
    }

}

