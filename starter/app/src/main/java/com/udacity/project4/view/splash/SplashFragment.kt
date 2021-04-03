package com.udacity.project4.view.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.udacity.project4.R
import com.udacity.project4.databinding.FragmentSplashBinding
import com.udacity.project4.utils.AuthManager

/**Created by
Author: Ankush Bose
Date: 03,April,2021
 **/

class SplashFragment : Fragment() {

    private var binding: FragmentSplashBinding? = null
    private lateinit var authManager: AuthManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ticker to check whether the user is logged in
        val timer = object : CountDownTimer(1000, 3000) {
            override fun onTick(millisUntilFinished: Long) {
                // utilising time, time is money sir!
                authManager = AuthManager(requireActivity())
            }

            override fun onFinish() {
                checkIfLoggedIn()
            }
        }
        timer.start()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            activity?.finishAffinity()
        }
    }

    private fun checkIfLoggedIn() {
        if (!authManager.isUserLoggedIn)
            findNavController().navigate(R.id.action_splashFragment_to_authenticationFragment)
        else findNavController().navigate(R.id.action_splashFragment_to_reminderListFragment)
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}