package me.sukru.carpettravel.presentation.favorite_station

import me.sukru.carpettravel.domain.model.Station

data class FavoriteStationUiState(
    val isLoading: Boolean = false,
    val favoriteStationList: List<Station> = emptyList(),
    val favoriteStationCount: Int = 0,
    val error: String? = null,
)
