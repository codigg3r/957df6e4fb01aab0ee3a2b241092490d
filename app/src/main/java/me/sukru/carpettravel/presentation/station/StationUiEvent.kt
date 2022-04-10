package me.sukru.carpettravel.presentation.station

sealed class StationUiEvent {
    data class ShowError(val errorMessage: String) : StationUiEvent()
    data class ShowDialog(val message: String, val title: String) : StationUiEvent()
}