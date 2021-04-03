package com.udacity.project4.utils

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * Author: Ankush Bose
 * On: 10/Feb/2021
 */
class AuthManager(
    private val activity: Activity,
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    val isUserLoggedIn: Boolean
        get() {
            return auth.currentUser != null
        }

    val userDetails: FirebaseUser?
        get() {
            return if (isUserLoggedIn) auth.currentUser else null
        }

    /*val userEmail: String?
        get() {
            return if (isUserLoggedIn) auth.currentUser?.email else null
        }

    val userId: String?
        get() {
            return if (isUserLoggedIn) auth.currentUser?.uid else null
        }*/

    private val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build()
    )

    fun authUser() = activity.startActivityForResult(
        // intent
        AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build(),
        // request code
        RC_SIGN_IN
    )

    fun handleAuth(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        action: (isSuccessful: Boolean, exception: Exception?) -> Unit
    ) {
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) action.invoke(true, null)
            else response?.error?.let { action.invoke(false, it) }
        }

    }

    fun signOut(): LiveData<ResultData<Boolean>> = liveData { emit(startSignOut()) }
    private fun startSignOut(): ResultData<Boolean> = try {
        AuthUI.getInstance().signOut(activity)
        ResultData.Success(true)
    } catch (e: Exception) {
        ResultData.Failed(e.message)
    }

    companion object {
        const val RC_SIGN_IN = 123
    }
}