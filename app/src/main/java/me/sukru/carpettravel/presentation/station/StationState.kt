package me.sukru.carpettravel.presentation.station

import me.sukru.carpettravel.domain.model.Station

data class StationState(
    val ugs: Double = 0.0,
    val eus: Double = 0.0,
    val ds: Double = 0.0,
    val spaceShipName: String = "",
    val spaceShipHealth: Int = 0,
    val stationList: List<Station> = emptyList(),
    val currentStationName: String = "",
    val searchText: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val dsTimer: Double = 0.0,
)
