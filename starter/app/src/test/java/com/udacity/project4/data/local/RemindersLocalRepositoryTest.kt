package com.udacity.project4.data.local

import android.app.Application
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.project4.utils.REMINDER_DATABASE_NAME
import com.udacity.project4.view.locationreminders.data.FakeDataSource
import junit.framework.TestCase
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidApplication
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

/**
 * Created by
 * Author: Ankush Bose
 * Date: 08,April,2021
 */

@RunWith(AndroidJUnit4::class)
class RemindersLocalRepositoryTest : TestCase() {

    private lateinit var repository: FakeDataSource

    @Before
    fun setup() {

    }


}