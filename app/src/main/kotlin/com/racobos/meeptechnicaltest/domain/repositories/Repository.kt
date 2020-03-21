package com.racobos.meeptechnicaltest.domain.repositories

import com.google.android.gms.maps.model.LatLng
import com.racobos.meeptechnicaltest.android.entities.VehicleMarker
import com.racobos.meeptechnicaltest.domain.errors.NetworkError

interface Repository {
    @Throws(NetworkError::class)
    suspend fun retrieveVehiclesByLocation(
        lowerLeftLatLon: LatLng,
        upperRightLatLon: LatLng
    ): List<VehicleMarker>
}