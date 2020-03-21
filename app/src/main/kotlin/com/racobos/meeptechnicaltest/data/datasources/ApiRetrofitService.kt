package com.racobos.meeptechnicaltest.data.datasources

import com.racobos.meeptechnicaltest.data.dtos.VehicleApi
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRetrofitService {
    @GET("/tripplan/api/v1/routers/lisboa/resources")
    suspend fun retrieveVehiclesFromArea(
        @Query("lowerLeftLatLon") lowerLeftLocation: String,
        @Query("upperRightLatLon") upperRightLocation: String
    ): List<VehicleApi>
}