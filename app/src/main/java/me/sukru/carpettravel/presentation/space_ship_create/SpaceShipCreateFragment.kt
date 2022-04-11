package me.sukru.carpettravel.presentation.space_ship_create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import me.sukru.carpettravel.R
import me.sukru.carpettravel.common.BindingFragment
import me.sukru.carpettravel.common.extensions.showAlertDialog
import me.sukru.carpettravel.databinding.FragmentCreateSpaceShipBinding

@AndroidEntryPoint
class SpaceShipCreateFragment : BindingFragment<FragmentCreateSpaceShipBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentCreateSpaceShipBinding::inflate

    private val viewModel: SpaceShipCreateViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUiToViewModel()
        collectStateFlow()
        collectUiEvents()
    }

    private fun bindUiToViewModel() {
        binding.apply {
            tiSpaceshipName.addTextChangedListener {
                viewModel.onNameChanged(it?.toString() ?: "")
            }
            rsSpeed.setOnChangeListener {
                viewModel.onSpeedChanged(it)

            }
            rsStrength.setOnChangeListener {
                viewModel.onStrengthChanged(it)
            }
            rsCapacity.setOnChangeListener {
                viewModel.onCapacityChanged(it)
            }
            btnContinue.setOnClickListener {
                viewModel.onContinueClicked()
            }
        }
    }

    private fun collectStateFlow() {
        lifecycleScope.launchWhenCreated {
            viewModel.state.collect {
                bindViewModelToUi(it)
            }
        }
    }

    private fun collectUiEvents() {
        viewModel.uiEvent.observe(viewLifecycleOwner) {
            when (it) {
                is SpaceShipUiEvent.Navigate -> {
                    findNavController().apply {
                        navigate(it.id)
                    }
                }
                is SpaceShipUiEvent.ShowError -> {
                    showAlertDialog(it.errorMessage, getString(R.string.error))
                }
            }
        }
    }


    private fun bindViewModelToUi(state: SpaceShipState) {
        binding.apply {
            if (tiSpaceshipName.text?.toString() != state.name) {
                tiSpaceshipName.setText(state.name)
            }
            rsSpeed.value = state.speed
            rsSpeed.maxValue = state.speedMaxValue
            rsStrength.value = state.strength
            rsStrength.maxValue = state.strengthMaxValue
            rsCapacity.value = state.capacity
            rsCapacity.maxValue = state.capacityMaxValue
            tvSpaceshipPoints.text = state.remainingPoint

        }
    }
}