package com.udacity.project4.view.savereminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.udacity.project4.R
import com.udacity.project4.databinding.FragmentSaveReminderBinding
import com.udacity.project4.utils.setDisplayHomeAsUpEnabled
import com.udacity.project4.utils.showSnack
import com.udacity.project4.view.reminderslist.ReminderDataItem
import com.udacity.project4.viewmodel.SaveReminderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveReminderFragment : Fragment() {

    private val _viewModel: SaveReminderViewModel by viewModels()
    private var binding: FragmentSaveReminderBinding? = null

    private var selectedLocationName: String = ""
    private var selectedLatitude: Int = 0
    private var selectedLongitude: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_save_reminder, container, false)
        selectedLatitude =
            SaveReminderFragmentArgs.fromBundle(requireArguments()).selectedLatitude
        selectedLongitude =
            SaveReminderFragmentArgs.fromBundle(requireArguments()).selectedLongitute
        selectedLocationName =
            SaveReminderFragmentArgs.fromBundle(requireArguments()).selectedLocationName.toString()

        return binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = _viewModel
            locationName = selectedLocationName
            setDisplayHomeAsUpEnabled(true)
        }?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObserver()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding?.apply {
            selectLocation.setOnClickListener {
                findNavController()
                    .navigate(R.id.action_saveReminderFragment_to_selectLocationFragment)
            }
            saveReminder.setOnClickListener {
                saveReminderData()
                startGeoFencing()
            }

            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                goToReminderList()
            }

//            TODO: use the user entered reminder details to:
//             1) add a geofencing request - done
//             2) save the reminder to the local db
        }
    }

    private fun startGeoFencing() {

    }


    private fun saveReminderData() {
        val title = binding?.reminderTitle?.text.toString()
        val description = binding?.reminderDescription?.text.toString()
        val location = selectedLocationName
        val latitude = selectedLatitude.toDouble()
        val longitude = selectedLongitude.toDouble()
        val reminder = ReminderDataItem(title, description, location, latitude, longitude)
        _viewModel.validateAndSaveReminder(reminder)
        goToReminderList()
    }

    private fun setObserver() {
        _viewModel.showMessage.observe(viewLifecycleOwner, { message ->
            message?.let { showSnack(requireView(), it) }
        })
    }

    private fun goToReminderList() {
        findNavController().navigate(R.id.action_saveReminderFragment_to_reminderListFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
