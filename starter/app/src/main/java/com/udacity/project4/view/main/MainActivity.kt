package com.udacity.project4.view.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.udacity.project4.R
import com.udacity.project4.utils.AuthManager
import com.udacity.project4.utils.showSnack
import kotlinx.android.synthetic.main.activity_main.*

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

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }*/

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

