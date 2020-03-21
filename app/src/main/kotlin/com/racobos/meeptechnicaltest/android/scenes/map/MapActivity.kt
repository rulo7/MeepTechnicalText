package com.racobos.meeptechnicaltest.android.scenes.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.maps.android.clustering.ClusterManager
import com.racobos.meeptechnicaltest.R
import com.racobos.meeptechnicaltest.android.tools.displaySnackBar
import com.racobos.meeptechnicaltest.android.tools.maps.VehiclesCluster
import com.racobos.meeptechnicaltest.domain.toQueryLatLon
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.layout_app_toolbar.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private val mapViewModel: MapViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        setSupportActionBar(materialToolbar)
        setTitle(R.string.app_name)
        observeError()
        (map as SupportMapFragment).getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.setOnMapLoadedCallback {
            googleMap.setOnCameraIdleListener {
                val clusterManager = setClusterManager(googleMap)
                observeMarkers(clusterManager)
                onCameraIdle(googleMap)
            }
            moveCameraToInitialPosition(googleMap)
        }
    }

    private fun observeMarkers(clusterManager: ClusterManager<VehiclesCluster>) {
        mapViewModel.markers.observe(this, Observer {
            clusterManager.clearItems()
            it.forEach { marker ->
                clusterManager.addItem(
                    VehiclesCluster(
                        marker.position,
                        marker.name,
                        marker.color
                    )
                )
            }
            clusterManager.cluster()
        })
    }

    private fun setClusterManager(googleMap: GoogleMap): ClusterManager<VehiclesCluster> {
        val clusterManager: ClusterManager<VehiclesCluster> by inject {
            parametersOf(this, googleMap, ::onCameraIdle)
        }
        clusterManager.setOnClusterItemClickListener { marker ->
            VehicleDetailsFragment.newInstance(
                marker.title,
                marker.position.toQueryLatLon(),
                marker.color.toString()
            ).show(supportFragmentManager, this::class.java.name)
            true
        }
        return clusterManager
    }

    private fun onCameraIdle(googleMap: GoogleMap) {
        val latLngBounds = googleMap.projection.visibleRegion.latLngBounds
        progress.show()
        mapViewModel.retrieveData(latLngBounds.southwest, latLngBounds.northeast, progress::hide)
    }

    private fun moveCameraToInitialPosition(googleMap: GoogleMap) {
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(mapViewModel.cameraPosition.value, 0)
        )
    }

    private fun observeError() {
        mapViewModel.error.observe(this, Observer { error ->
            error.displaySnackBar(this)
        })
    }
}