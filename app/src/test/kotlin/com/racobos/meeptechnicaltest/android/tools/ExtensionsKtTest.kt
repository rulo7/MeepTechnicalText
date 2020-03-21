package com.racobos.meeptechnicaltest.android.tools

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.racobos.meeptechnicaltest.android.scenes.map.LOWER_LEFT_LAT
import com.racobos.meeptechnicaltest.android.scenes.map.LOWER_LEFT_LON
import com.racobos.meeptechnicaltest.android.scenes.map.UPPER_RIGHT_LAT
import com.racobos.meeptechnicaltest.android.scenes.map.UPPER_RIGHT_LON
import org.junit.Assert
import org.junit.Test

class ExtensionsKtTest {
    @Test
    fun `LatLngBounds should return false if invoke isNotInside with a LatLngBounds included in`() {
        val containedBounds = LatLngBounds(
            LatLng(
                LOWER_LEFT_LAT + 0.005,
                LOWER_LEFT_LON + 0.005
            ), LatLng(
                UPPER_RIGHT_LAT - 0.005,
                UPPER_RIGHT_LON - 0.005
            )
        )
        val bounds = LatLngBounds(
            LatLng(
                LOWER_LEFT_LAT,
                LOWER_LEFT_LON
            ), LatLng(
                UPPER_RIGHT_LAT,
                UPPER_RIGHT_LON
            )
        )

        Assert.assertTrue(!containedBounds.isNotInside(bounds))
    }

    @Test
    fun `LatLngBounds should return true if invoke isNotInside with a LatLngBounds not included in`() {
        val containedBounds = LatLngBounds(
            LatLng(
                LOWER_LEFT_LAT - 0.005,
                LOWER_LEFT_LON - 0.005
            ), LatLng(
                UPPER_RIGHT_LAT + 0.005,
                UPPER_RIGHT_LON + 0.005
            )
        )
        val bounds = LatLngBounds(
            LatLng(
                LOWER_LEFT_LAT,
                LOWER_LEFT_LON
            ), LatLng(
                UPPER_RIGHT_LAT,
                UPPER_RIGHT_LON
            )
        )

        Assert.assertTrue(containedBounds.isNotInside(bounds))
    }
}