package me.sukru.carpettravel.presentation.favorite_station

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import me.sukru.carpettravel.common.BindingFragment
import me.sukru.carpettravel.databinding.FragmentFavoriteStationBinding

@AndroidEntryPoint
class FavoriteStationFragment: BindingFragment<FragmentFavoriteStationBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentFavoriteStationBinding::inflate
}