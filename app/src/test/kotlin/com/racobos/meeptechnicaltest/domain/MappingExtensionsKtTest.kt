package com.racobos.meeptechnicaltest.domain

import com.google.android.gms.maps.model.LatLng
import com.racobos.meeptechnicaltest.android.entities.VehicleMarker
import com.racobos.meeptechnicaltest.data.dtos.VehicleApi
import com.racobos.meeptechnicaltest.domain.errors.ParseError
import org.junit.Assert
import org.junit.Test

class MappingExtensionsKtTest {

    @Test
    fun `VehicleApi should throw ParseError when invoke toVehicleMarker() with null y or x`() {
        try {
            VehicleApi(y = null, x = -1.0).toVehicleMarker()
            VehicleApi(y = -1.0, x = null).toVehicleMarker()
            VehicleApi(y = null, x = null).toVehicleMarker()
        } catch (e: ParseError) {
            Assert.assertTrue(true)
            return
        }
        Assert.assertTrue(false)
    }

    @Test
    fun `VehicleApi should return a valid VehicleMarker when invoke toVehicleMarker() with non null y or x`() {
        val y = 1.0
        val x = -1.0
        val name = "test"
        val companyZoneId = "378"
        val result =
            VehicleApi(
                name = name,
                y = y,
                x = x,
                companyZoneId = companyZoneId
            ).toVehicleMarker()

        val expected = VehicleMarker(name, LatLng(y, x), companyZoneId.toMarkerColor())
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `LatLng should return a concat string when invoke toQueryLatLon`() {
        val lat = -1.0234
        val lon = 0.0012
        val latLon = LatLng(lat, lon)
        val expected = "$lat,$lon"
        val result = latLon.toQueryLatLon()
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `String should not return values lesser than 0 or equals or greater than 360 when invoke toMarkerColor with values between 378,545`() {
        val lowestResult = "378".toMarkerColor()
        val highestResult = "545".toMarkerColor()
        Assert.assertTrue(lowestResult >= 0f)
        Assert.assertTrue(highestResult >= 0f)
        Assert.assertTrue(lowestResult < 360f)
        Assert.assertTrue(highestResult < 360f)
        val midResult = "400".toMarkerColor()
        // result-> 47,29...
        Assert.assertTrue(midResult > 47.28f)
        Assert.assertTrue(midResult < 47.30f)

    }
}