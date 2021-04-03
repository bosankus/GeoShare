package com.udacity.project4.view.reminderdetails

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.udacity.project4.R
import com.udacity.project4.view.reminderslist.ReminderDataItem

/**
 * Activity that displays the reminder details after the user clicks on the notification
 */
class FragmentReminderDetails : Fragment(R.layout.fragment_reminder_details) {

    companion object {
        private const val EXTRA_ReminderDataItem = "EXTRA_ReminderDataItem"

        // receive the reminder object after the user clicks on the notification
        fun newIntent(context: Context, reminderDataItem: ReminderDataItem): Intent {
            val intent = Intent(context, FragmentReminderDetails::class.java)
            intent.putExtra(EXTRA_ReminderDataItem, reminderDataItem)
            return intent
        }
    }
}
