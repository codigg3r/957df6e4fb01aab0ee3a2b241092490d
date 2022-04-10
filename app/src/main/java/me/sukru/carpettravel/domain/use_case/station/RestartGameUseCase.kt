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
            val oldSpaceStations = localDataSource.getSpaceStations()
            val oldSpaceShip = localDataSource.getSpaceShip()
            val spaceShip = oldSpaceShip.copy(
                eus = oldSpaceShip.eusInitial,
                ugs = oldSpaceShip.ugsInitial,
                ds = oldSpaceShip.dsInitial,
                dsTimer = oldSpaceShip.dsTimerInitial,
            )
            localDataSource.insertSpaceShip(spaceShip)
            localDataSource.deleteAllSpaceStations()
            val spaceStations = remoteDataSource.getSpaceStations().map { newSpaceStation ->
                oldSpaceStations.firstOrNull { it.name == newSpaceStation.name }?.let {
                    newSpaceStation.copy(
                        isFavorite = it.isFavorite,
                        isVisited = it.name == "Dünya",
                    )
                } ?: newSpaceStation
            }
            localDataSource.insertSpaceStations(spaceStations)
        }
    }
}