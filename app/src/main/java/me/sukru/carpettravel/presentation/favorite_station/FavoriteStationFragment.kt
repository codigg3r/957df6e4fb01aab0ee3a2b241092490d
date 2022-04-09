package me.sukru.carpettravel.presentation.favorite_station

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import me.sukru.carpettravel.common.BindingFragment
import me.sukru.carpettravel.databinding.FragmentFavoriteStationBinding

class FavoriteStationFragment: BindingFragment<FragmentFavoriteStationBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentFavoriteStationBinding::inflate
}