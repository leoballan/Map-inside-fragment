package com.vila.mapinsidefragment

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.vila.mapinsidefragment.databinding.FragmentBlankBinding


class BlankFragment : Fragment(R.layout.fragment_blank) {

    companion object{
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
    }

    private val PERMISSION = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private lateinit var mFusedLocationClient : FusedLocationProviderClient
    private lateinit var lastLocation : Location

    private var _binding : FragmentBlankBinding? = null
    private val binding get() = _binding


    private val viewmodel : MapViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBlankBinding.bind(view)
        if(savedInstanceState== null){
            init()

        }

    }

    private fun init(){
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
        if (checkForLocationPermissions()){
            getMyCurrentLocation()
        }else{
            askLocationPermissions()
        }
    }

    private fun checkForLocationPermissions() = PERMISSION.all {
        ContextCompat.checkSelfPermission(
            activity!!.applicationContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun askLocationPermissions() {
        val requestPermission =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission->
                if (permission.all { it.value }){
                    getMyCurrentLocation()
                    Log.d("mwebservice"," ++++++ All Permisses are accepted")
                }else {
                    Log.d("mwebservice", "++++++ Permisses not granted")
                }
            }
        requestPermission.launch(PERMISSION)
    }

    private fun getMyCurrentLocation() {
        try {
            val locationResult = mFusedLocationClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.getResult() != null) {
                        lastLocation = task.getResult()!!

                        /*val bundle = bundleOf(
                            LATITUDE to lastLocation.latitude
                            , LONGITUDE to lastLocation.longitude)
*/
                        viewmodel.setMyLocation(lastLocation)
                        parentFragmentManager.commit {
                            setReorderingAllowed(true)
                            add<MapsFragment>(R.id.container)
                        }
                        parentFragmentManager.commit {
                            setReorderingAllowed(true)
                            add<MapsFragment>(R.id.container2)
                        }

                    }
                }
            }
        } catch (e: SecurityException) {
            Toast.makeText(activity, "Ha ocurrido un error .." + e, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}