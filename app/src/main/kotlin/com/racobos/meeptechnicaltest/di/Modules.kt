package com.racobos.meeptechnicaltest.di

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.ClusterRenderer
import com.racobos.meeptechnicaltest.android.scenes.map.MapViewModel
import com.racobos.meeptechnicaltest.android.tools.maps.VehiclesCluster
import com.racobos.meeptechnicaltest.android.tools.maps.VehiclesClusterRenderer
import com.racobos.meeptechnicaltest.data.datasources.DAOs
import com.racobos.meeptechnicaltest.domain.repositories.Repository
import com.racobos.meeptechnicaltest.domain.repositories.RetrofitRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val dataModule = module {
    single<Repository> { RetrofitRepository(DAOs.retrofitDAO) }
}

val androidModule = module {
    factory<ClusterRenderer<VehiclesCluster>> { (context: Context, map: GoogleMap, manager: ClusterManager<VehiclesCluster>) ->
        VehiclesClusterRenderer(
            context,
            map,
            manager
        )
    }

    factory { (context: Context, map: GoogleMap, onCameraIdle: (GoogleMap) -> Unit) ->
        ClusterManager<VehiclesCluster>(
            context,
            map
        ).apply {
            renderer = get { parametersOf(context, map, this) }
            map.setOnCameraIdleListener {
                onCameraIdle(map)
                onCameraIdle()
            }
            map.setOnMarkerClickListener(this)
        }
    }

    viewModel { MapViewModel(get()) }
}