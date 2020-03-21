package com.racobos.meeptechnicaltest.domain

import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.racobos.meeptechnicaltest.android.entities.VehicleMarker
import com.racobos.meeptechnicaltest.data.dtos.VehicleApi
import com.racobos.meeptechnicaltest.domain.errors.ParseError

const val DEFAULT_MARKER_NAME = "Unknown"
const val DEFAULT_MARKER_COLOR = BitmapDescriptorFactory.HUE_ORANGE


@Throws(ParseError::class)
fun VehicleApi.toVehicleMarker() =
    if (x == null || y == null) throw ParseError("y or x can not be null")
    else VehicleMarker(
        name = name ?: DEFAULT_MARKER_NAME,
        position = LatLng(y, x),
        color = companyZoneId?.toMarkerColor() ?: DEFAULT_MARKER_COLOR
    )

fun LatLng.toQueryLatLon() = "$latitude,$longitude"

// The marker color has to be a float in range [0.0,360.0) and companyZoneId min is 378 max is 545
fun String.toMarkerColor() = ((this.toFloat() - 378f) * 359f) / (545f - 378f)