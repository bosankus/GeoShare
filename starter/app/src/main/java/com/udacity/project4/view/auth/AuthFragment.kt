package com.udacity.project4.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.udacity.project4.R
import com.udacity.project4.databinding.FragmentAuthBinding
import com.udacity.project4.utils.AuthManager
import com.udacity.project4.utils.showSnack

/**Created by
Author: Ankush Bose
Date: 03,April,2021
 **/

class AuthFragment : Fragment() {

    private lateinit var authManager: AuthManager
    private var binding: FragmentAuthBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authManager = AuthManager(requireActivity())
        setClickListeners()
    }

    private fun setClickListeners() {
        binding?.fragmentAuthEmailAuth?.setOnClickListener { startAuth() }
    }

    private fun startAuth() {
        if (!authManager.isUserLoggedIn) authManager.authUser()
        else showSnack(requireView(), "Already signedIn")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}
