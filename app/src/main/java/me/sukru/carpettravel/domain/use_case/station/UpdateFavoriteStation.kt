package me.sukru.carpettravel.domain.use_case.station

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.sukru.carpettravel.data.local.CarpetTravelLocalRepositoryImpl
import me.sukru.carpettravel.data.toEntity
import me.sukru.carpettravel.domain.model.Station
import javax.inject.Inject

class UpdateFavoriteStation @Inject constructor(
    private val localRepositoryImpl: CarpetTravelLocalRepositoryImpl
) {
    suspend operator fun invoke(station: Station) {
        withContext(Dispatchers.IO) {
            localRepositoryImpl.updateSpaceStation(station.copy(isFavorite = station.isFavorite.not()).toEntity())
        }
    }
}