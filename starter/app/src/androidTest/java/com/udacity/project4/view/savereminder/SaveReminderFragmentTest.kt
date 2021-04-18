package com.udacity.project4.view.savereminder

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.R
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by
 * Author: Ankush Bose
 * Date: 15,April,2021
 */

@RunWith(AndroidJUnit4::class)
@MediumTest
class SaveReminderFragmentTest {

    @Test
    fun addReminder_addsReminderToLocal_returnsNoLocationSelected() {

        val title = "Sample location name"
        val description = "Sample description"

        launchFragmentInContainer<SaveReminderFragment>(Bundle(), R.style.AppTheme)

        onView(withId(R.id.reminderTitle)).perform(typeText(title))
        onView(withId(R.id.reminderDescription)).perform(typeText(description))

        Espresso.closeSoftKeyboard()

        onView(withId(R.id.saveReminder)).perform(click())

        // assert that snackbar is showing success message option
        assertThat(
            onView(
                allOf(
                    withId(com.google.android.material.R.id.snackbar_text),
                    withText(R.string.select_location)
                )
            ).check(matches(isDisplayed()))
        )

    }
}