package com.udacity.project4.view.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.udacity.project4.R
import com.udacity.project4.utils.AuthManager
import com.udacity.project4.utils.showSnack

/**
 * The RemindersActivity that holds the reminders fragments
 */
class MainActivity : AppCompatActivity() {

    private lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        authManager = AuthManager(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            authManager.handleAuth(requestCode, resultCode, data) { isSuccessful, error ->
                if (isSuccessful && authManager.userDetails != null && error == null)
                    goToReminderFragment()
            }
        } else showSnack(findViewById(R.id.activity_main), "Failed to authenticate")
    }

    private fun goToReminderFragment() {
        findNavController(R.id.nav_host_fragment)
            .navigate(R.id.action_authenticationFragment_to_reminderListFragment)
    }

}

