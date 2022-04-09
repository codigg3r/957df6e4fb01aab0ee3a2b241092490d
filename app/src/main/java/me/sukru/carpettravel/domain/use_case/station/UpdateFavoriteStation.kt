package me.sukru.carpettravel.domain.use_case.station

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.withContext
import me.sukru.carpettravel.data.local.CarpetTravelLocalDataSource
import me.sukru.carpettravel.data.toEntity
import me.sukru.carpettravel.domain.model.Station
import javax.inject.Inject

class UpdateFavoriteStation @Inject constructor(
    private val localDataSource: CarpetTravelLocalDataSource
) {
    suspend operator fun invoke(station: Station) {
        withContext(Dispatchers.IO) {
            localDataSource.updateSpaceStation(station.copy(isFavorite = station.isFavorite.not()).toEntity())
        }
    }
}