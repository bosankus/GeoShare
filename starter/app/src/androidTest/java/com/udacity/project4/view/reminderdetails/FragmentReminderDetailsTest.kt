package com.udacity.project4.view.reminderdetails

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.R
import com.udacity.project4.data.model.Reminder
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by
 * Author: Ankush Bose
 * Date: 14,April,2021
 */

@RunWith(AndroidJUnit4::class)
@MediumTest
class FragmentReminderDetailsTest {

    @Test
    fun activeReminderDetails_DisplayInUi() {

        val reminder = Reminder(
            "Testing location",
            "Sample descriptio",
            "location name",
            22.647596,
            88.645856,
            "123456"
        )

        val bundle = FragmentReminderDetailsArgs(reminder).toBundle()

        launchFragmentInContainer<FragmentReminderDetails>(bundle, R.style.AppTheme)


    }
}