package com.udacity.project4.view.reminderslist

import android.os.Bundle
import android.view.*
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.udacity.project4.R
import com.udacity.project4.base.NavigationCommand
import com.udacity.project4.databinding.FragmentRemindersBinding
import com.udacity.project4.utils.*
import com.udacity.project4.viewmodel.RemindersListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReminderListFragment : Fragment() {

    //use Koin to retrieve the ViewModel instance
    private val _viewModel: RemindersListViewModel by viewModel()

    private var binding: FragmentRemindersBinding? = null

    private lateinit var authManager: AuthManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_reminders, container, false
            )
        return binding?.apply {
            viewModel = _viewModel
            setHasOptionsMenu(true)
            refreshLayout.setOnRefreshListener { _viewModel.loadReminders() }
            setDisplayHomeAsUpEnabled(false)
            setTitle(getString(R.string.app_name))
        }?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = requireActivity()
            addReminderFAB.setOnClickListener { navigateToAddReminder() }
            authManager = AuthManager(requireActivity())
            setupRecyclerView()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            activity?.finishAffinity()
        }
    }

    override fun onResume() {
        super.onResume()
        //load the reminders list on the ui
        _viewModel.loadReminders()
    }

    private fun navigateToAddReminder() {
        findNavController().navigate(R.id.to_save_reminder)
    }

    private fun setupRecyclerView() {
        val adapter = RemindersListAdapter {
        }

//        setup the recycler view using the extension function
        binding?.reminderssRecyclerView?.setup(adapter)
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
            }
        })
    }
}
