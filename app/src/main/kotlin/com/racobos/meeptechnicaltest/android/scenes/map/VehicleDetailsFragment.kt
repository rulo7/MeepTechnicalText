package com.racobos.meeptechnicaltest.android.scenes.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.racobos.meeptechnicaltest.R
import kotlinx.android.synthetic.main.fragment_vehicles_detail.*

class VehicleDetailsFragment : BottomSheetDialogFragment() {
    companion object {
        private const val NAME_KEY = "name"
        private const val LOCATION_KEY = "location"
        private const val COLOR_KEY = "color"
        fun newInstance(
            name: String,
            location: String,
            color: String
        ) = VehicleDetailsFragment().apply {
            arguments = bundleOf(
                Pair(NAME_KEY, name),
                Pair(LOCATION_KEY, location),
                Pair(COLOR_KEY, color)
            )
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_vehicles_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(NAME_KEY)?.apply(nameTextInputEditText::setText)
        arguments?.getString(LOCATION_KEY)?.apply(locationTextInputEditText::setText)
        arguments?.getString(COLOR_KEY)?.apply(colorTextInputEditText::setText)
    }
}