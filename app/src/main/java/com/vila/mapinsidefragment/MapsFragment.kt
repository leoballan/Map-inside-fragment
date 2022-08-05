package com.vila.mapinsidefragment

import android.Manifest
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.vila.mapinsidefragment.databinding.FragmentMapsBinding

class MapsFragment : Fragment(R.layout.fragment_maps), OnMapReadyCallback{


    private val PERMISSION = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private lateinit var mGoogleMap: GoogleMap
    private var _viewBinding : FragmentMapsBinding? = null
    private val binding get() = _viewBinding

    private val viewmodel : MapViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewBinding = FragmentMapsBinding.bind(view)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)


    }

    override fun onMapReady(map: GoogleMap) {
        mGoogleMap = map
        with(mGoogleMap) {
            moveCamera(
                com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        viewmodel.myLocation.value!!.latitude,viewmodel.myLocation.value!!.longitude
                    ), 15f
                )
            )
        }
        mGoogleMap.addMarker(MarkerOptions().position(LatLng(viewmodel.myLocation.value!!.latitude,viewmodel.myLocation.value!!.longitude)).title("Mi casa"))
    }








}