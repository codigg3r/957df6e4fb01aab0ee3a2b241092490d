package me.sukru.carpettravel.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.viewbinding.ViewBinding
import me.sukru.carpettravel.R
import me.sukru.carpettravel.common.BindingFragment
import me.sukru.carpettravel.databinding.FragmentHomeBinding

class HomeFragment : BindingFragment<FragmentHomeBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.home_nav_host_fragment) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHostFragment.navController)
    }
}