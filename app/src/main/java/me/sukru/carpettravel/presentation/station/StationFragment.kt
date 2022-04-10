package me.sukru.carpettravel.presentation.station

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import me.sukru.carpettravel.common.BindingFragment
import me.sukru.carpettravel.common.extensions.nDecimal
import me.sukru.carpettravel.common.extensions.showWarning
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
        observeViewModel()
    }

    private fun bindUiToViewModel() {
        binding.search.addTextChangedListener {
            viewModel.search(it.toString())
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenResumed {
            viewModel.state.collect {
                withContext(Dispatchers.Main) {
                    bindViewModelToUi(it)
                }
            }
        }
        viewModel.uiEvent.observe(viewLifecycleOwner) {
            when(it) {
                is StationUiEvent.ShowError -> {
                    showWarning(it.errorMessage, "Error")
                }
                is StationUiEvent.ShowDialog -> {
                    showWarning(it.message, it.title)
                }
            }
        }
    }

    private fun bindViewModelToUi(it: StationState) {
        binding.apply {
            loading.isVisible = it.isLoading
            ugs.text = "UGS\n${it.ugs.nDecimal(2)}"
            eus.text = "EUS\n${it.eus.nDecimal(2)}"
            ds.text = "DS\n${it.ds.nDecimal(2)}"
            spaceshipHealth.text = "Spaceship Health\n${it.spaceShipHealth}"
            spaceshipTime.text = "Spaceship Time\n${it.dsTimer.nDecimal(2)}s"
            spaceshipName.text = it.spaceShipName
            it.currentStation?.name?.let { currentStationName ->
                currentStation.text = "Current Station: $currentStationName"
            }
            if (stationAdapter == null) {
                stationAdapter = StationAdapter(onTravelClickListener, onFavoriteClickListener)
            }
            if (stationList.adapter == null) {
                stationList.adapter = stationAdapter
            }
            stationAdapter?.submitList(it.stationList)
        }
    }
}