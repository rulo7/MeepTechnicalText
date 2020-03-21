package com.racobos.meeptechnicaltest.data.dtos

data class VehicleApi(
    val id: String? = null,
    val name: String? = null,
    val x: Double? = null,
    val y: Double? = null,
    val scheduledArrival: String? = null,
    val locationType: String? = null,
    val companyZoneId: String? = null,
    val lat: Double? = null,
    val lon: Double? = null
)