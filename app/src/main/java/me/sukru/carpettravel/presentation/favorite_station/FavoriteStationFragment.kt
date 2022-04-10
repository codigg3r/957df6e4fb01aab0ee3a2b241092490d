package me.sukru.carpettravel.presentation.favorite_station

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import me.sukru.carpettravel.common.BindingFragment
import me.sukru.carpettravel.common.extensions.showWarning
import me.sukru.carpettravel.databinding.FragmentFavoriteStationBinding
import me.sukru.carpettravel.domain.model.Station
import me.sukru.carpettravel.presentation.station.StationAdapter

@AndroidEntryPoint
class FavoriteStationFragment: BindingFragment<FragmentFavoriteStationBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentFavoriteStationBinding::inflate

    private val viewModel: FavoriteStationViewModel by viewModels()
    private var favoriteStationAdapter: FavoriteStationAdapter? = null

    private val onFavoriteClickListener: (Station) -> Unit = {
        viewModel.favoriteStation(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenResumed {
            viewModel.state.collect {
                bindViewModelToUi(it)
            }
        }
    }

    private fun bindViewModelToUi(state: FavoriteStationUiState) {
        binding.apply {
            count.text = state.favoriteStationCount.toString()
            if (state.error != null) {
                showWarning(state.error, "Error")
            }
            if (favoriteStationAdapter == null) {
                favoriteStationAdapter = FavoriteStationAdapter(onFavoriteClickListener)
            }
            if (favoriteStationList.adapter == null) {
                favoriteStationList.adapter = favoriteStationAdapter
            }
            favoriteStationAdapter?.submitList(state.favoriteStationList)
        }
    }
}