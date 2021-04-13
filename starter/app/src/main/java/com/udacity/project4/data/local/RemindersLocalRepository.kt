package com.udacity.project4.data.local

import com.udacity.project4.data.model.Reminder
import com.udacity.project4.data.dto.Result
import kotlinx.coroutines.*

/**
 * Concrete implementation of a data source as a db.
 *
 * The repository is implemented so that you can focus on only testing it.
 *
 * @param remindersDao the dao that does the Room db operations
 * @param ioDispatcher a coroutine dispatcher to offload the blocking IO tasks
 */
class RemindersLocalRepository(private val remindersDao: RemindersDao) : ReminderDataSource {

    /**
     * Get the reminders list from the local db
     * @return Result the holds a Success with all the reminders or an Error object with the error message
     */
    override fun getReminders() = remindersDao.getReminders()

    /**
     * Insert a reminder in the db.
     * @param reminder the reminder to be inserted
     */
    override suspend fun saveReminder(reminder: Reminder) =
        withContext(Dispatchers.IO) {
           remindersDao.saveReminder(reminder)
        }

    /**
     * Get a reminder by its id
     * @param id to be used to get the reminder
     * @return Result the holds a Success object with the Reminder or an Error object with the error message
     */
    override suspend fun getReminder(id: String): Result<Reminder> = withContext(Dispatchers.IO) {
        try {
            val reminder: Reminder? = remindersDao.getReminderById(id)
            if (reminder != null) {
                return@withContext Result.Success(reminder)
            } else {
                return@withContext Result.Error("Reminder not found!")
            }
        } catch (e: Exception) {
            return@withContext Result.Error(e.localizedMessage)
        }
    }

    /**
     * Deletes all the reminders in the db
     */
    override suspend fun deleteAllReminders() {
        withContext(Dispatchers.IO) {
            remindersDao.deleteAllReminders()
        }
    }
}
