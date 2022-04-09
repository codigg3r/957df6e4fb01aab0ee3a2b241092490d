package me.sukru.carpettravel.presentation.home

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import me.sukru.carpettravel.common.BindingFragment
import me.sukru.carpettravel.databinding.FragmentHomeBinding

class HomeFragment: BindingFragment<FragmentHomeBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentHomeBinding::inflate

}