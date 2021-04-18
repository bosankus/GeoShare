package com.udacity.project4

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

/**Created by
Author: Ankush Bose
Date: 17,April,2021
 **/
class AndroidTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, AndroidTestApp::class.java.name, context)
    }

}