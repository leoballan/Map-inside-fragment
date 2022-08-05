package com.vila.mapinsidefragment

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapViewModel :ViewModel() {

    val myLocation : MutableLiveData<Location> by lazy { MutableLiveData() }
    val clientLocation : MutableLiveData<Location> by lazy { MutableLiveData() }

    fun setMyLocation(location :Location){
        myLocation.value = location
    }

    fun setClientLocation(location :Location){
        myLocation.value = location
    }
}