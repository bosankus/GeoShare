package com.udacity.project4.viewmodel

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.R
import com.udacity.project4.data.model.Reminder
import com.udacity.project4.getOrAwaitValueTest
import com.udacity.project4.view.locationreminders.data.FakeDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.annotation.Config

/**
 * Created by
 * Author: Ankush Bose
 * Date: 13,April,2021
 */

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(maxSdk = Build.VERSION_CODES.P, minSdk = Build.VERSION_CODES.P)
class SaveReminderViewModelTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: SaveReminderViewModel

    @Before
    fun setUp() {

        viewModel =
            SaveReminderViewModel(ApplicationProvider.getApplicationContext(), FakeDataSource())
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun saveReminder_saveReminderListToLocal_returnsTrue() = runBlockingTest {

        val reminder = Reminder(
            "Testing location",
            "Sample descriptio",
            "location name",
            22.647596,
            88.645856,
            "123456"
        )

        viewModel.validateAndSaveReminder(reminder)

        val value = viewModel.isReminderSaved.getOrAwaitValueTest()

        val isLoading = viewModel.showLoading.getOrAwaitValueTest()

        assertThat(isLoading).isFalse()
        assertThat(value).isTrue()
    }

    @Test
    fun saveReminder_saveReminderListToLocal_returnsEmptyTitle() = runBlockingTest {

        val reminder = Reminder(
            "",
            "Sample descriptio",
            "location name",
            22.647596,
            88.645856,
            "123456"
        )

        viewModel.validateAndSaveReminder(reminder)

        val value = viewModel.showMessage.getOrAwaitValueTest()

        assertThat(value).contains(context.resources.getString(R.string.select_title))
    }

    @Test
    fun saveReminder_saveReminderListToLocal_returnsEmptyLatOrLng() = runBlockingTest {

        val reminder = Reminder(
            "sample title",
            "Sample descriptio",
            "location name",
            0.0,
            88.645856,
            "123456"
        )

        viewModel.validateAndSaveReminder(reminder)

        val value = viewModel.showMessage.getOrAwaitValueTest()

        assertThat(value).contains(context.resources.getString(R.string.select_location))
    }
}