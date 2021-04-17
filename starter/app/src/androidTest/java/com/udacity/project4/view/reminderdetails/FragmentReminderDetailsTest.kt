package com.udacity.project4.view.reminderdetails

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.R
import com.udacity.project4.data.model.Reminder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by
 * Author: Ankush Bose
 * Date: 14,April,2021
 */

@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
class FragmentReminderDetailsTest {

    @Test
    fun activeReminderDetails_DisplayInUi() = runBlockingTest {

        val reminder = Reminder(
            "Testing location",
            "Sample description",
            "location name",
            22.647596,
            88.645856,
            "123456"
        )

        val bundle = FragmentReminderDetailsArgs(reminder).toBundle()
        launchFragmentInContainer<FragmentReminderDetails>(bundle, R.style.AppTheme)

        onView(withId(R.id.reminder_details_title)).check(matches(withText("Testing location")))
        onView(withId(R.id.reminder_details_title)).check(matches(isDisplayed()))

        onView(withId(R.id.reminder_details_desc)).check(matches(withText("Sample description")))
        onView(withId(R.id.reminder_details_desc)).check(matches(isDisplayed()))

        onView(withId(R.id.reminder_details_location)).check(matches(withText("location name")))
        onView(withId(R.id.reminder_details_location)).check(matches(isDisplayed()))

        onView(withId(R.id.reminder_details_latitude)).check(matches(withText("22.647596")))
        onView(withId(R.id.reminder_details_latitude)).check(matches(isDisplayed()))

        onView(withId(R.id.reminder_details_longitude)).check(matches(withText("88.645856")))
        onView(withId(R.id.reminder_details_longitude)).check(matches(isDisplayed()))
    }
}