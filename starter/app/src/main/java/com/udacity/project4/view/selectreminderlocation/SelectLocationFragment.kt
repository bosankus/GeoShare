package com.udacity.project4.view.selectreminderlocation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.OnMapReadyCallback
import com.google.android.libraries.maps.model.BitmapDescriptorFactory
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MapStyleOptions.loadRawResourceStyle
import com.google.android.libraries.maps.model.MarkerOptions
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.udacity.project4.R
import com.udacity.project4.databinding.FragmentSelectLocationBinding
import com.udacity.project4.utils.*
import com.udacity.project4.viewmodel.SaveReminderViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

class SelectLocationFragment : Fragment(), OnMapReadyCallback {

    private val viewModel by sharedViewModel<SaveReminderViewModel>()
    private var binding: FragmentSelectLocationBinding? = null

    private var locationName: String = ""
    private var longitude: Double? = null
    private var latitude: Double? = null
    private var map: GoogleMap? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_select_location, container, false)
        return binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            mapView.onCreate(savedInstanceState)
            setHasOptionsMenu(true)
            setDisplayHomeAsUpEnabled(true)
        }?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.mapView?.getMapAsync(this)
        setOnClickListener()
    }


    private fun setOnClickListener() {
        binding?.selectLocation?.setOnClickListener {
            if (latitude != null && longitude != null && locationName.isNotEmpty()) {
                val action =
                    SelectLocationFragmentDirections
                        .actionSelectLocationFragmentToSaveReminderFragment(
                            latitude?.toFloat()!!,
                            longitude?.toFloat()!!
                        )
                findNavController().navigate(action)
            } else showSnack(requireView(), "Please select a location")
        }
    }


    override fun onMapReady(googleMap: GoogleMap?) {
        this.map = googleMap
        val homeLatLng = LatLng(22.667230, 88.401310)
        val zoomLevel = 15f
        val style = loadRawResourceStyle(requireContext(), R.raw.style_json)
        map?.setMapStyle(style)
        map?.apply {
            moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
            addMarker(MarkerOptions().position(homeLatLng))
            setMapLongClick(this)
            setPoiClick(this)
            enableCurrentLocationAndGeoFencing()
        }
    }


    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapClickListener {
            latitude = it.latitude.formatDouble()
            longitude = it.longitude.formatDouble()
            locationName = "lat: $latitude, long: $longitude"
            showSnack(requireView(), locationName)

            viewModel.location.value = locationName
            viewModel.latitude.value = latitude
            viewModel.longitude.value = longitude

            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1$.5f, Long: %2$.5f",
                it.latitude,
                it.longitude
            )
            map.apply { clear() }
                .addMarker(
                    MarkerOptions()
                        .position(it)
                        .title("Selected location")
                        .snippet(snippet)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
                )
        }
    }


    private fun setPoiClick(map: GoogleMap) {
        map.setOnPoiClickListener {
            latitude = it.latLng.latitude
            longitude = it.latLng.longitude
            locationName = String.format(Locale.ENGLISH, it.name)
            showSnack(requireView(), locationName)

            viewModel.location.value = locationName
            viewModel.latitude.value = latitude
            viewModel.longitude.value = longitude

            val poiMarker = map.apply { clear() }
                .addMarker(
                    MarkerOptions()
                        .position(it.latLng)
                        .title(it.name)
                )
            poiMarker.showInfoWindow()
        }
    }


    @SuppressLint("MissingPermission")
    // Permissions are already being checked
    private fun enableCurrentLocationAndGeoFencing() {
        if (Build.VERSION.SDK_INT >= ANDROID_10)
            requireActivity().runWithPermissions(FINE_LOCATION, COARSE_LOCATION) {
                map?.isMyLocationEnabled = true
            }
        else map?.isMyLocationEnabled = true
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_options, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.normal_map -> {
            map?.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            map?.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            map?.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            map?.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }


    override fun onResume() {
        super.onResume()
        binding?.mapView?.onResume()
    }


    override fun onStart() {
        super.onStart()
        binding?.mapView?.onStart()
    }


    override fun onStop() {
        super.onStop()
        binding?.mapView?.onStop()
    }


    override fun onPause() {
        super.onPause()
        binding?.mapView?.onPause()
    }


    override fun onLowMemory() {
        super.onLowMemory()
        binding?.mapView?.onLowMemory()
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding?.mapView?.onSaveInstanceState(outState)
    }
}
