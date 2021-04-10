package com.udacity.project4.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.data.model.Reminder
import com.udacity.project4.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Unit test the DAO
@SmallTest
class RemindersDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: RemindersDatabase
    private lateinit var remindersDao: RemindersDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemindersDatabase::class.java
        ).allowMainThreadQueries().build()
        remindersDao = database.reminderDao()
    }

    @After
    fun teardown() {
        database.close()
    }


    @Test
    fun insertReminderItem() = runBlockingTest {
        // sample data for testing
        val reminder =
            Reminder(
                "Testing location",
                "Sample descriptio",
                "location name",
                22.647596,
                88.645856,
                "123456"
            )
        remindersDao.saveReminder(reminder)

        val allReminderItems = remindersDao.getReminders().getOrAwaitValue()

        assertThat(allReminderItems).contains(reminder)
    }

    @Test
    fun deleteReminderItem() = runBlockingTest {
        // sample data for testing
        val reminder =
            Reminder(
                "Testing location",
                "Sample descriptio",
                "location name",
                22.647596,
                88.645856,
                "123456"
            )
        remindersDao.saveReminder(reminder)
        remindersDao.deleteAllReminders()

        val allReminders = remindersDao.getReminders().getOrAwaitValue()

        assertThat(allReminders).doesNotContain(reminder)
    }

    @Test
    fun getReminders() = runBlockingTest {
        // sample data for testing
        val reminderOne =
            Reminder(
                "Testing location",
                "Sample descriptio",
                "location name",
                22.647596,
                88.645856,
                "123456"
            )
        val reminderTwo = Reminder(
            "Testing location two",
            "Sample descriptio",
            "location name",
            22.647596,
            88.645856,
            "1234561"
        )

        remindersDao.saveReminder(reminderOne)
        remindersDao.saveReminder(reminderTwo)

        val allReminders = remindersDao.getReminders().getOrAwaitValue()
        assertThat(allReminders).containsAtLeast(reminderOne, reminderTwo)
    }

    @Test
    fun getReminderById() = runBlockingTest {
        // sample data for testing
        val reminder =
            Reminder(
                "Testing location",
                "Sample descriptio",
                "location name",
                22.647596,
                88.645856,
                "123456"
            )
        remindersDao.saveReminder(reminder)
        val reminderItem = remindersDao.getReminderById("123456")
        assertThat(reminderItem).isNotNull()
    }
}