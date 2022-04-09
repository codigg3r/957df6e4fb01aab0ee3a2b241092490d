package me.sukru.carpettravel.presentation.space_ship_create

sealed class SpaceShipUiEvent {
    data class ShowError(val errorMessage: String) : SpaceShipUiEvent()
    data class Navigate(val id: Int) : SpaceShipUiEvent()
}