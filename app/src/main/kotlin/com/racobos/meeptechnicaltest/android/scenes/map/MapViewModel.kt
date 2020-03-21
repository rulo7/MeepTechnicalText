package com.racobos.meeptechnicaltest.android.scenes.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.racobos.meeptechnicaltest.R
import com.racobos.meeptechnicaltest.android.entities.VehicleMarker
import com.racobos.meeptechnicaltest.android.tools.isNotInside
import com.racobos.meeptechnicaltest.domain.repositories.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val LOWER_LEFT_LAT = 38.711046
const val LOWER_LEFT_LON = -9.160096
const val UPPER_RIGHT_LAT = 38.739429
const val UPPER_RIGHT_LON = -9.137115

class MapViewModel(private val repository: Repository) : ViewModel() {

    val cameraPosition: MutableLiveData<LatLngBounds> by lazy {
        MutableLiveData(
            LatLngBounds(
                LatLng(
                    LOWER_LEFT_LAT,
                    LOWER_LEFT_LON
                ),
                LatLng(
                    UPPER_RIGHT_LAT,
                    UPPER_RIGHT_LON
                )
            )
        )
    }

    val markers: MutableLiveData<List<VehicleMarker>> by lazy {
        MutableLiveData<List<VehicleMarker>>()
    }
    val error: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    fun retrieveData(lowerLeftLatLong: LatLng, upperRightLatLng: LatLng, onFinish: () -> Unit) {
        val bounds = LatLngBounds(lowerLeftLatLong, upperRightLatLng)
        if (bounds.isNotInside(cameraPosition.value))
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val data =
                        repository.retrieveVehiclesByLocation(lowerLeftLatLong, upperRightLatLng)
                    markers.postValue(data)
                } catch (e: Throwable) {
                    error.postValue(R.string.error_message)
                } finally {
                    viewModelScope.launch(Dispatchers.Main) {
                        onFinish()
                    }
                }
            }
        else onFinish()
        cameraPosition.value = bounds
    }
}