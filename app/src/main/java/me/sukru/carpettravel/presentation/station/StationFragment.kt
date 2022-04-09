package me.sukru.carpettravel.presentation.station

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }

    private fun bindViewModelToUi(it: StationState) {
        binding.apply {
            loading.isVisible = it.isLoading
            ugs.text = "UGS\n${it.ugs}"
            eus.text = "EUS\n${it.eus}"
            ds.text = "DS\n${it.ds}"
            spaceshipHealth.text = "Spaceship Health\n${it.spaceShipHealth}"
            spaceshipTime.text = "Spaceship Time\n${it.dsTimer}s"
            spaceshipName.text = it.spaceShipName
            if (it.error != null) {
                showWarning(it.error, "Error")
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