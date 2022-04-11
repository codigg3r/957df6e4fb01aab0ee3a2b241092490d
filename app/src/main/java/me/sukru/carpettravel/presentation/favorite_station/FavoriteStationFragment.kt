package me.sukru.carpettravel.presentation.favorite_station

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import me.sukru.carpettravel.R
import me.sukru.carpettravel.common.BindingFragment
import me.sukru.carpettravel.common.extensions.showAlertDialog
import me.sukru.carpettravel.databinding.FragmentFavoriteStationBinding
import me.sukru.carpettravel.domain.model.Station

@AndroidEntryPoint
class FavoriteStationFragment: BindingFragment<FragmentFavoriteStationBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentFavoriteStationBinding::inflate

    private val viewModel: FavoriteStationViewModel by viewModels()
    private var favoriteStationAdapter: FavoriteStationAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteStationAdapter = FavoriteStationAdapter(this::onFavoriteClick)
        collectStateFlow()
    }

    private fun onFavoriteClick(station: Station) {
        viewModel.favoriteStation(station)
    }

    private fun collectStateFlow() {
        lifecycleScope.launchWhenResumed {
            viewModel.state.collect {
                bindViewModelToUi(it)
            }
        }
    }

    private fun bindViewModelToUi(state: FavoriteStationUiState) {
        binding.apply {
            tvCount.text = state.favoriteStationCount.toString()
            if (state.error != null) {
                showAlertDialog(state.error, getString(R.string.error))
            }
            if (rvFavoriteStationList.adapter == null) {
                rvFavoriteStationList.adapter = favoriteStationAdapter
            }
            favoriteStationAdapter?.submitList(state.favoriteStationList)
        }
    }
}