package com.udacity.project4.view.savereminder

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.MyApp
import com.udacity.project4.R
import com.udacity.project4.data.local.FakeRemindersLocalRepositoryTest
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin


/**
 * Created by
 * Author: Ankush Bose
 * Date: 15,April,2021
 */

@RunWith(AndroidJUnit4::class)
class SaveReminderFragmentTest {

    private lateinit var scenario: FragmentScenario<SaveReminderFragment>

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer(themeResId = R.style.AppTheme)
        scenario.moveToState(Lifecycle.State.CREATED)
    }

    @Test
    fun addReminder_addsReminderToLocal_returnsNoLocationSelected() {
        val title = "Sample location name"
        val description = "Sample description"
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