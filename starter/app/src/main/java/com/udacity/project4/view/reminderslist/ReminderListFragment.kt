package com.udacity.project4.view.reminderslist

import android.os.Bundle
import android.view.*
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.udacity.project4.R
import com.udacity.project4.databinding.FragmentRemindersBinding
import com.udacity.project4.utils.*
import com.udacity.project4.viewmodel.RemindersListViewModel
import org.koin.android.ext.android.inject

class ReminderListFragment : Fragment() {

    val _viewModel by inject<RemindersListViewModel>()
    private var binding: FragmentRemindersBinding? = null
    private lateinit var authManager: AuthManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reminders, container, false)
        setHasOptionsMenu(true)
        setDisplayHomeAsUpEnabled(false)
        setTitle(getString(R.string.app_name))
        return binding?.apply {
            viewModel = _viewModel
        }?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = requireActivity()
            authManager = AuthManager(requireActivity())
            setOnClickListeners()
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            activity?.finishAffinity()
        }
    }

    private fun setOnClickListeners() {
        binding?.addReminderFAB?.setOnClickListener {
            requireActivity().runWithPermissions(FINE_LOCATION, COARSE_LOCATION) {
                navigateToAddReminder()
            }
        }
    }

    private fun navigateToAddReminder() {
        findNavController().navigate(R.id.to_save_reminder)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) logOut()
        return super.onOptionsItemSelected(item)
    }

    private fun logOut() {
        authManager.signOut().observe(viewLifecycleOwner, {
            if (it is ResultData.Success) {
                findNavController().navigate(R.id.action_reminderListFragment_to_authenticationFragment)
                showSnack(requireView(), "You are logged out")
            } else if (it is ResultData.Failed) showSnack(requireView(), "Could not log out!")
        })
    }
}
