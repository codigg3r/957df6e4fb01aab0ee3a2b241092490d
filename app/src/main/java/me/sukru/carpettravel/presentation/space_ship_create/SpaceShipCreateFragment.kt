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
import me.sukru.carpettravel.common.BindingFragment
import me.sukru.carpettravel.common.extensions.showWarning
import me.sukru.carpettravel.databinding.FragmentCreateSpaceShipBinding

@AndroidEntryPoint
class SpaceShipCreateFragment : BindingFragment<FragmentCreateSpaceShipBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentCreateSpaceShipBinding::inflate

    private val viewModel: SpaceShipCreateViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUiToViewModel()
        observeViewModel()
    }

    private fun bindUiToViewModel() {
        binding.apply {
            spaceshipName.addTextChangedListener {
                viewModel.onNameChanged(it?.toString() ?: "")
            }
            speed.setOnChangeListener {
                viewModel.onSpeedChanged(it)

            }
            strength.setOnChangeListener {
                viewModel.onStrengthChanged(it)
            }
            capacity.setOnChangeListener {
                viewModel.onCapacityChanged(it)
            }
            continueButton.setOnClickListener {
                viewModel.onContinueClicked()
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenCreated {
            viewModel.state.collect {
                bindViewModelToUi(it)
            }
        }
        viewModel.uiEvent.observe(viewLifecycleOwner) {
            when(it) {
                is SpaceShipUiEvent.Navigate -> {
                    findNavController().apply {
                        navigate(it.id)
                    }
                }
                is SpaceShipUiEvent.ShowError -> {
                    findNavController().apply {
                        showWarning(it.errorMessage, "Error")
                    }
                }
            }
        }
    }

    private fun bindViewModelToUi(state: SpaceShipState) {
        binding.apply {
            if (spaceshipName.text?.toString() != state.name) {
                spaceshipName.setText(state.name)
            }
            speed.value = state.speed
            speed.maxValue = state.speedMaxValue
            strength.value = state.strength
            strength.maxValue = state.strengthMaxValue
            capacity.value = state.capacity
            capacity.maxValue = state.capacityMaxValue
            spaceShipPoints.text = state.remainingPoint

        }
    }
}