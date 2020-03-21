package com.racobos.meeptechnicaltest.android.tools.maps

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class VehiclesCluster(
    private val latLng: LatLng,
    private val title: String,
    val color: Float
) : ClusterItem {
    override fun getSnippet(): String = ""

    override fun getTitle(): String = title

    override fun getPosition(): LatLng = latLng
}

class VehiclesClusterRenderer(
    context: Context,
    map: GoogleMap,
    manager: ClusterManager<VehiclesCluster>
) : DefaultClusterRenderer<VehiclesCluster>(context, map, manager) {
    override fun onBeforeClusterItemRendered(
        item: VehiclesCluster?,
        markerOptions: MarkerOptions?
    ) {
        if (item != null && markerOptions != null)
            markerOptions.icon(
                BitmapDescriptorFactory.defaultMarker(item.color)
            )
        super.onBeforeClusterItemRendered(item, markerOptions)
    }
}