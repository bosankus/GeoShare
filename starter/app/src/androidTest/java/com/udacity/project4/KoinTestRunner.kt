package com.udacity.project4

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

/**Created by
Author: Ankush Bose
Date: 14,April,2021
 **/

class KoinTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application? {
        return super.newApplication(cl, MyApp::class.java.name, context)
    }
}
