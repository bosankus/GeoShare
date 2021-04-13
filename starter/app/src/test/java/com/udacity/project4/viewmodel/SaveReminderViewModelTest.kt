package com.udacity.project4.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.data.model.Reminder
import com.udacity.project4.getOrAwaitValueTest
import com.udacity.project4.view.locationreminders.data.FakeDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
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

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: SaveReminderViewModel

    @Before
    fun setUp() {

        viewModel =
            SaveReminderViewModel(ApplicationProvider.getApplicationContext(), FakeDataSource())
    }


    @Test
    fun saveReminder_saveReminderListToLocal() = runBlockingTest {

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

        assertThat(value).isTrue()

    }
}