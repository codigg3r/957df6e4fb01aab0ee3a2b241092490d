package me.sukru.carpettravel.presentation.station

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import me.sukru.carpettravel.common.BindingFragment
import me.sukru.carpettravel.databinding.FragmentStationBinding

class StationFragment : BindingFragment<FragmentStationBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentStationBinding::inflate

}