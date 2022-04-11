package me.sukru.carpettravel.presentation.station

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import me.sukru.carpettravel.R
import me.sukru.carpettravel.common.BindingFragment
import me.sukru.carpettravel.common.extensions.nDecimal
import me.sukru.carpettravel.common.extensions.showAlertDialog
import me.sukru.carpettravel.databinding.FragmentStationBinding
import me.sukru.carpettravel.domain.model.Station

@AndroidEntryPoint
class StationFragment : BindingFragment<FragmentStationBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentStationBinding::inflate
    private val viewModel: StationViewModel by viewModels()

    private var stationAdapter: StationAdapter? = null

    private val onTravelClickListener: (Station) -> Unit = {
        viewModel.travelStation(it)
    }

    private val onFavoriteClickListener: (Station) -> Unit = {
        viewModel.favoriteStation(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUiToViewModel()
        collectStateFlow()
        observeUiEvents()
    }

    private fun bindUiToViewModel() {
        binding.etSearch.addTextChangedListener {
            viewModel.search(it.toString())
        }
    }

    private fun collectStateFlow() {
        lifecycleScope.launchWhenResumed {
            viewModel.state.collect {
                withContext(Dispatchers.Main) {
                    bindViewModelToUi(it)
                }
            }
        }
    }

    private fun observeUiEvents() {
        viewModel.uiEvent.observe(viewLifecycleOwner) {
            when(it) {
                is StationUiEvent.ShowError -> {
                    showAlertDialog(it.errorMessage, getString(R.string.error))
                }
                is StationUiEvent.ShowDialog -> {
                    showAlertDialog(it.message, it.title)
                }
            }
        }
    }

    private fun bindViewModelToUi(it: StationState) {
        binding.apply {
            pbLoading.isVisible = it.isLoading
            tvUgs.text = getString(R.string.ugs_left, it.ugs)
            tvEus.text = getString(R.string.eus_left, it.eus)
            tvDs.text = getString(R.string.ds_left, it.ds)
            tvSpaceshipHealth.text = getString(R.string.spaceship_health_left, it.spaceShipHealth)
            tvSpaceshipTime.text = getString(R.string.spaceship_time_left, it.dsTimer)
            tvSpaceshipName.text = it.spaceShipName
            it.currentStation?.name?.let { currentStationName ->
                tvCurrentStation.text = getString(R.string.current_station, currentStationName)
            }
            if (stationAdapter == null) {
                stationAdapter = StationAdapter(onTravelClickListener, onFavoriteClickListener)
            }
            if (rvStationList.adapter == null) {
                rvStationList.adapter = stationAdapter
            }
            stationAdapter?.submitList(it.stationList)
        }
    }
}