package me.sukru.carpettravel.domain.use_case.station

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.sukru.carpettravel.data.local.CarpetTravelLocalDataSource
import me.sukru.carpettravel.data.remote.CarpetTravelRemoteDataSource
import javax.inject.Inject

class RestartGameUseCase @Inject constructor(
    private val localDataSource: CarpetTravelLocalDataSource,
    private val remoteDataSource: CarpetTravelRemoteDataSource,
) {
    suspend operator fun invoke() {
        withContext(Dispatchers.IO) {
            localDataSource.deleteAllSpaceShips()
            val spaceStations = remoteDataSource.getSpaceStations()
            localDataSource.insertSpaceStations(spaceStations.map {
                if (it.name == "Dünya") it.copy(
                    isCurrentStation = true,
                    isVisited = true
                ) else it
            })
        }
    }
}