package com.udacity.project4.view.reminderslist

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.R
import com.udacity.project4.data.local.LocalDB
import com.udacity.project4.data.local.ReminderDataSource
import com.udacity.project4.data.local.RemindersDao
import com.udacity.project4.data.local.RemindersLocalRepository
import com.udacity.project4.data.model.Reminder
import com.udacity.project4.viewmodel.RemindersListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
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
class ReminderListFragmentTest : AutoCloseKoinTest() {

    private val appContext: Application = ApplicationProvider.getApplicationContext()
    private lateinit var mockViewModel: RemindersListViewModel
    private lateinit var repository: ReminderDataSource

    private val testModule = module {
        single { LocalDB.createRemindersDao(appContext) }
        single { RemindersLocalRepository(get() as RemindersDao) }
    }

    @Before
    fun setUp() {
        mockViewModel = mock(RemindersListViewModel::class.java)
        loadKoinModules(testModule)
        repository = get() as RemindersLocalRepository
    }

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

        val job = launch {
            repository.saveReminder(reminderOne)
            repository.saveReminder(reminderTwo)
        }

        val scenario = launchFragmentInContainer<ReminderListFragment>(Bundle(), R.style.AppTheme)

        val navController = mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(withId(R.id.reminderssRecyclerView)).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText("Testing location One")), click()
            )
        )

        verify(navController).navigate(
            ReminderListFragmentDirections.actionReminderListFragmentToFragmentReminderDetails(
                reminderOne
            )
        )

        job.cancel()
    }

}