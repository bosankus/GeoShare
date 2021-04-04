package com.udacity.project4.view.savereminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.udacity.project4.R
import com.udacity.project4.databinding.FragmentSaveReminderBinding
import com.udacity.project4.utils.setDisplayHomeAsUpEnabled
import com.udacity.project4.viewmodel.SaveReminderViewModel
import org.koin.android.ext.android.inject

class SaveReminderFragment : Fragment() {

    private val _viewModel: SaveReminderViewModel by inject()
    private var binding: FragmentSaveReminderBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_save_reminder, container, false)

        return binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = _viewModel
            setDisplayHomeAsUpEnabled(true)
        }?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding?.apply {
            selectLocation.setOnClickListener {
                findNavController()
                    .navigate(R.id.action_saveReminderFragment_to_selectLocationFragment)
            }

            saveReminder.setOnClickListener {
                val title = _viewModel.reminderTitle.value
                val description = _viewModel.reminderDescription
                val location = _viewModel.reminderSelectedLocationStr.value
                val latitude = _viewModel.latitude
                val longitude = _viewModel.longitude.value

//            TODO: use the user entered reminder details to:
//             1) add a geofencing request
//             2) save the reminder to the local db
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //make sure to clear the view model after destroy, as it's a single view model.
        _viewModel.onClear()
    }
}
