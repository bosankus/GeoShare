package com.udacity.project4.view.reminderslist

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.R
import com.udacity.project4.data.local.FakeRemindersLocalRepositoryTest
import com.udacity.project4.data.model.Reminder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * Created by
 * Author: Ankush Bose
 * Date: 17,April,2021
 */

@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
class ReminderListFragmentTest {

    private val repositoryTest = FakeRemindersLocalRepositoryTest()

    @Test
    fun clickOnReminderListItem_navigatesToReminderDetailsFragment() = runBlockingTest {

        val reminderOne = Reminder(
            "Testing location One",
            "Sample description One",
            "location name One",
            22.00,
            88.00,
            "1"
        )

        val reminderTwo = Reminder(
            "Testing location Two",
            "Sample description Two",
            "location name Two",
            22.01,
            88.01,
            "2"
        )

        repositoryTest.saveReminder(reminderOne)
        repositoryTest.saveReminder(reminderTwo)

        val scenario = launchFragmentInContainer<ReminderListFragment>(Bundle(), R.style.AppTheme)

        val navController = mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(withId(R.id.reminderssRecyclerView)).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText("ccd")), click()
            )
        )

        verify(navController).navigate(
            ReminderListFragmentDirections.actionReminderListFragmentToFragmentReminderDetails(
                reminderOne
            )
        )
    }
}