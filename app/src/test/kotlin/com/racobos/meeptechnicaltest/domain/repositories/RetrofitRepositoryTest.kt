package com.racobos.meeptechnicaltest.domain.repositories

import com.google.android.gms.maps.model.LatLng
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.racobos.meeptechnicaltest.data.datasources.ApiRetrofitService
import com.racobos.meeptechnicaltest.data.dtos.VehicleApi
import com.racobos.meeptechnicaltest.domain.errors.NetworkError
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class RetrofitRepositoryTest {

    @Test
    fun `RetrofitRepositoryTest should throw NetworkError when invoke retrieveVehiclesByLocation and it throws HttpException`() =
        runBlocking {
            val fakeLatLng = LatLng(-1.0, 1.0)
            val dao = mock<ApiRetrofitService> {
                onBlocking {
                    retrieveVehiclesFromArea(any(), any())
                }.doThrow(eq(FakeHttpException()))
            }
            val repository = RetrofitRepository(dao)

            try {
                repository.retrieveVehiclesByLocation(fakeLatLng, fakeLatLng)
            } catch (e: NetworkError) {
                assertTrue(true)
                return@runBlocking
            }

            assertTrue(false)
        }

    @Test
    fun `RetrofitRepositoryTest should doesn't throw any when invoke retrieveVehiclesByLocation and it not throws HttpException`() =
        runBlocking {
            val fakeLatLng = LatLng(-1.0, 1.0)
            val dao = mock<ApiRetrofitService> {
                onBlocking {
                    retrieveVehiclesFromArea(any(), any())
                }.doThrow(eq(FakeNoHttpException()))
            }
            val repository = RetrofitRepository(dao)

            try {
                repository.retrieveVehiclesByLocation(fakeLatLng, fakeLatLng)
            } catch (e: NetworkError) {
                assertTrue(false)
                return@runBlocking
            }

            assertTrue(true)
        }

    @Test
    fun `RetrofitRepositoryTest should return an empty list of VehicleMarkers when invoke retrieveVehiclesByLocation and returned list has not a valid set of VehicleApi`() =
        runBlocking {
            val fakeLatLng = LatLng(-1.0, 1.0)
            val dao = mock<ApiRetrofitService> {
                onBlocking {
                    retrieveVehiclesFromArea(any(), any())
                }.thenReturn(
                    listOf(
                        VehicleApi(),
                        VehicleApi(),
                        VehicleApi()
                    )
                )
            }
            val repository = RetrofitRepository(dao)

            try {
                val result = repository.retrieveVehiclesByLocation(fakeLatLng, fakeLatLng)
                assertTrue(result.isEmpty())
            } catch (e: NetworkError) {
                assertTrue(false)
                return@runBlocking
            }

            assertTrue(true)
        }


    @Test
    fun `RetrofitRepositoryTest should return an similar sized list of VehicleMarkers when invoke retrieveVehiclesByLocation and returned list has a valid set of VehicleApi`() =
        runBlocking {
            val fakeLatLng = LatLng(-1.0, 1.0)
            val dao = mock<ApiRetrofitService> {
                onBlocking {
                    retrieveVehiclesFromArea(any(), any())
                }.thenReturn(
                    listOf(
                        VehicleApi(y = 0.0, x = 0.0),
                        VehicleApi(y = 0.0, x = 0.0),
                        VehicleApi(y = 0.0, x = 0.0)
                    )
                )
            }
            val repository = RetrofitRepository(dao)

            try {
                val result = repository.retrieveVehiclesByLocation(fakeLatLng, fakeLatLng)
                Assert.assertEquals(3, result.size)
            } catch (e: NetworkError) {
                assertTrue(false)
                return@runBlocking
            }

            assertTrue(true)
        }
}

class FakeNoHttpException : RuntimeException()
class FakeHttpException : HttpException(Response.error<Any>(404, ResponseBody.create(any(), "")))