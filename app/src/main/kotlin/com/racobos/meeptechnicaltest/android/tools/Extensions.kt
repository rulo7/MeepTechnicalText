package com.racobos.meeptechnicaltest.android.tools

import android.app.Activity
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.material.snackbar.Snackbar

fun @receiver:StringRes Int.displaySnackBar(
    activity: Activity,
    @IdRes viewId: Int = android.R.id.content,
    duration: Int = Snackbar.LENGTH_LONG
) = Snackbar.make(activity.findViewById(viewId), this, duration).show()

fun LatLngBounds.isNotInside(latLngBounds: LatLngBounds?) = if (latLngBounds == null) true
else (!latLngBounds.contains(this.southwest) || !latLngBounds.contains(this.northeast))