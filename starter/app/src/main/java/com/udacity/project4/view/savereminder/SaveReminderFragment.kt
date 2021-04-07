package com.udacity.project4.view.savereminder

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.maps.model.LatLng
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.udacity.project4.R
import com.udacity.project4.databinding.FragmentSaveReminderBinding
import com.udacity.project4.utils.*
import com.udacity.project4.view.reminderslist.ReminderDataItem
import com.udacity.project4.viewmodel.SaveReminderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveReminderFragment : Fragment() {

    private val _viewModel: SaveReminderViewModel by viewModels()
    private lateinit var geoFencingClient: GeofencingClient
    private var binding: FragmentSaveReminderBinding? = null

    private val selectedLatitude: Int by lazy { SaveReminderFragmentArgs.fromBundle(requireArguments()).selectedLatitude }
    private val selectedLongitude by lazy { SaveReminderFragmentArgs.fromBundle(requireArguments()).selectedLongitute }
    private val selectedLocationName by lazy { SaveReminderFragmentArgs.fromBundle(requireArguments()).selectedLocationName.toString() }

    // Private variable to handle geoFence transitions
    private val geoFencePendingIntent: PendingIntent by lazy {
        val intent = Intent(requireContext(), GeofenceBroadcastReceiver::class.java)
        intent.action = ACTION_GEOFENCE_EVENT
        PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_save_reminder, container, false)

        return binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = _viewModel
            locationName = selectedLocationName
            setDisplayHomeAsUpEnabled(true)
        }?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        geoFencingClient = LocationServices.getGeofencingClient(requireContext())

        setObserver()
        setOnClickListeners()
    }

    private fun setObserver() {
        _viewModel.apply {
            showMessage.observe(viewLifecycleOwner, { message ->
                /*message?.let { showSnack(requireView(), it) }*/
            })
            isReminderSaved.observe(viewLifecycleOwner, { status ->

            })
        }
    }

    private fun setOnClickListeners() {
        requireActivity()
            .onBackPressedDispatcher.addCallback(viewLifecycleOwner) { goToReminderList() }

        binding?.apply {
            selectLocation.setOnClickListener {
                if (Build.VERSION.SDK_INT >= ANDROID_10)
                    requireActivity().runWithPermissions(BACKGROUND_LOCATION) { goToSelectLocation() }
                else goToSelectLocation()

            }
            saveReminder.setOnClickListener {
                if (selectedLatitude != 0 && selectedLongitude != 0) {
                    val latLng = LatLng(selectedLatitude.toDouble(), selectedLongitude.toDouble())
                    startGeoFencing(latLng)
                    saveReminderData()
                }
            }
        }
    }

    private fun startGeoFencing(latLng: LatLng) {
        val locations = arrayOf(latLng)
        val geoFences = locations.map {
            Geofence.Builder()
                .setRequestId(it.hashCode().toString())
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setCircularRegion(it.latitude, it.longitude, GEOFENCE_RADIUS)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .build()
        }
        val geoFencingRequest = GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            addGeofences(geoFences)
        }.build()

        addGeoFence(geoFencingRequest)
    }


    @SuppressLint("MissingPermission")
    private fun addGeoFence(geoFencingRequest: GeofencingRequest) {
        requireActivity().runWithPermissions(FINE_LOCATION, COARSE_LOCATION) {
            geoFencingClient.addGeofences(geoFencingRequest, geoFencePendingIntent).run {
                addOnFailureListener {
                    showSnack(requireView(), "Something went wrong ${it.localizedMessage}")
                }
                addOnSuccessListener {
                    goToReminderList()
                    showSnack(
                        requireView(),
                        "Geofencing activated on ${geoFencingRequest.geofences}"
                    )
                }
            }
        }
    }

    private fun saveReminderData() {
        val title = binding?.reminderTitle?.text.toString()
        val description = binding?.reminderDescription?.text.toString()
        val location = selectedLocationName
        val latitude = selectedLatitude.toDouble()
        val longitude = selectedLongitude.toDouble()
        val reminder = ReminderDataItem(title, description, location, latitude, longitude)
        _viewModel.validateAndSaveReminder(reminder)
    }

    private fun goToReminderList() {
        findNavController().navigate(R.id.action_saveReminderFragment_to_reminderListFragment)
    }

    private fun goToSelectLocation() {
        findNavController().navigate(R.id.action_saveReminderFragment_to_selectLocationFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
