package com.udacity.project4.view.main

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.udacity.project4.NavGraphDirections
import com.udacity.project4.R
import com.udacity.project4.data.model.Reminder
import com.udacity.project4.databinding.ActivityMainBinding
import com.udacity.project4.utils.*
import com.udacity.project4.view.reminderdetails.FragmentReminderDetailsArgs
import com.udacity.project4.view.reminderslist.ReminderListFragmentDirections
import com.udacity.project4.view.splash.SplashFragmentDirections
import timber.log.Timber

/**
 * The RemindersActivity that holds the reminders fragments
 */

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                as NavHostFragment
        navController = navHostFragment.navController

        navigateToReminderDetailsFragmentWhenNeeded(intent)

        authManager = AuthManager(this)
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
        navController.navigate(R.id.action_authenticationFragment_to_reminderListFragment)
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


    override fun onStart() {
        super.onStart()
        checkDeviceLocationSettings()
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToReminderDetailsFragmentWhenNeeded(intent)
    }


    private fun navigateToReminderDetailsFragmentWhenNeeded(intent: Intent?) {
        intent?.let {
            if (it.action == ACTION_DETAILS_FRAGMENT) {
                try {
                    val extras = it.extras?.get(EXTRA_ReminderDataItem) as Bundle
                    navController.navigate(R.id.action_global_reminderDetailsFragment, extras)
                } catch (e: Exception) {
                    Timber.i("$e")
                }
            }
        }
    }
}

