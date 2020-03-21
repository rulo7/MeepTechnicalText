import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.racobos.meeptechnicaltest.android.scenes.map.LOWER_LEFT_LAT
import com.racobos.meeptechnicaltest.android.scenes.map.LOWER_LEFT_LON
import com.racobos.meeptechnicaltest.android.scenes.map.UPPER_RIGHT_LAT
import com.racobos.meeptechnicaltest.android.scenes.map.UPPER_RIGHT_LON
import com.racobos.meeptechnicaltest.data.datasources.DAOs
import com.racobos.meeptechnicaltest.domain.toQueryLatLon
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ApiRequestsTest {
    @Test
    fun testRetrieveVehiclesFromArea() = runBlocking {
        val lowerLeftLatLon = LatLng(
            LOWER_LEFT_LAT,
            LOWER_LEFT_LON
        )
        val upperRightLatLon = LatLng(
            UPPER_RIGHT_LAT,
            UPPER_RIGHT_LON
        )

        val dao = DAOs.retrofitDAO
        val vehiclesApi = dao.retrieveVehiclesFromArea(
            lowerLeftLatLon.toQueryLatLon(),
            upperRightLatLon.toQueryLatLon()
        )

        vehiclesApi.forEach { vehicleApi ->
            if (vehicleApi.name == null ||
                vehicleApi.x == null ||
                vehicleApi.y == null ||
                vehicleApi.companyZoneId == null
            ) {
                System.err.println("Inconsistent data retrieved from the service, there are required fields that are not being retrieved in the response")
                System.err.println("The inconsistent model has the following fields:")
                System.err.println(Gson().toJson(vehicleApi))
                Assert.assertTrue(false)
                return@runBlocking
            }
        }
        if (vehiclesApi.isEmpty()) println("The test can't assure the consistency of the data because the request respond an empty list")
        else println("The list of VehicleApi retrieved from the request has a consistent structure")
        Assert.assertTrue(true)
    }
}