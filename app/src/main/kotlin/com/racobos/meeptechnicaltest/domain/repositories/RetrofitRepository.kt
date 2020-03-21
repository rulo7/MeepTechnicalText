package com.racobos.meeptechnicaltest.domain.repositories

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.racobos.meeptechnicaltest.android.entities.VehicleMarker
import com.racobos.meeptechnicaltest.data.datasources.ApiRetrofitService
import com.racobos.meeptechnicaltest.domain.errors.NetworkError
import com.racobos.meeptechnicaltest.domain.toQueryLatLon
import com.racobos.meeptechnicaltest.domain.toVehicleMarker
import retrofit2.HttpException

class RetrofitRepository(private val dao: ApiRetrofitService) : Repository {

    companion object {
        const val TAG = "RetrofitRepository"
    }

    override suspend fun retrieveVehiclesByLocation(
        lowerLeftLatLon: LatLng,
        upperRightLatLon: LatLng
    ): List<VehicleMarker> =
        manageRequestsErrors(
            "There was an error in the request: retrieveVehiclesByLocation",
            listOf()
        ) {
            dao.retrieveVehiclesFromArea(
                lowerLeftLocation = lowerLeftLatLon.toQueryLatLon(),
                upperRightLocation = upperRightLatLon.toQueryLatLon()
            ).mapNotNull {
                manageParsingErrors(it::toVehicleMarker)
            }
        }

    private fun <T> manageParsingErrors(block: () -> T): T? = try {
        block()
    } catch (e: Throwable) {
        Log.e(TAG, "There was an error when parsing", e)
        null
    }

    @Throws(NetworkError::class)
    private suspend fun <T> manageRequestsErrors(
        defaultMessage: String,
        default: T,
        block: suspend () -> T
    ): T = try {
        block()
    } catch (e: Throwable) {
        when (e) {
            is HttpException -> throw NetworkError(e.message())
            else -> Log.e(TAG, defaultMessage, e)
        }
        default
    }
}