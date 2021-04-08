 package com.udacity.project4.view.reminderdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.udacity.project4.R
import com.udacity.project4.databinding.FragmentReminderDetailsBinding

/**
 * Activity that displays the reminder details after the user clicks on the notification
 */

class FragmentReminderDetails : Fragment() {

    private var binding: FragmentReminderDetailsBinding? = null
    private val reminderArgs by lazy {
        FragmentReminderDetailsArgs.fromBundle(requireArguments()).reminderItem
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_reminder_details, container, false)
        return binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            reminderItem = reminderArgs
        }?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    /*companion object {
        private const val EXTRA_ReminderDataItem = "EXTRA_ReminderDataItem"

        // receive the reminder object after the user clicks on the notification
        fun newIntent(context: Context, reminderDataItem: ReminderDataItem): Intent {
            val intent = Intent(context, FragmentReminderDetails::class.java)
            intent.putExtra(EXTRA_ReminderDataItem, reminderDataItem)
            return intent
        }
    }*/
}
