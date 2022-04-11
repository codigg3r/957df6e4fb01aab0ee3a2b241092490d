package me.sukru.carpettravel.domain.use_case.station

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.sukru.carpettravel.data.local.CarpetTravelLocalRepositoryImpl
import me.sukru.carpettravel.data.remote.CarpetTravelRemoteRepositoryImpl
import javax.inject.Inject

class RestartGameUseCase @Inject constructor(
    private val localRepositoryImpl: CarpetTravelLocalRepositoryImpl,
    private val remoteRepositoryImpl: CarpetTravelRemoteRepositoryImpl,
) {
    suspend operator fun invoke() {
        withContext(Dispatchers.IO) {
            val oldSpaceStations = localRepositoryImpl.getSpaceStations()
            val oldSpaceShip = localRepositoryImpl.getSpaceShip()
            val spaceShip = oldSpaceShip.copy(
                eus = oldSpaceShip.eusInitial,
                ugs = oldSpaceShip.ugsInitial,
                ds = oldSpaceShip.dsInitial,
                dsTimer = oldSpaceShip.dsTimerInitial,
            )
            localRepositoryImpl.insertSpaceShip(spaceShip)
            localRepositoryImpl.deleteAllSpaceStations()
            val spaceStations = remoteRepositoryImpl.getSpaceStations().map { newSpaceStation ->
                oldSpaceStations.firstOrNull { it.name == newSpaceStation.name }?.let {
                    newSpaceStation.copy(
                        isFavorite = it.isFavorite,
                        isVisited = it.name == "Dünya",
                    )
                } ?: newSpaceStation
            }
            localRepositoryImpl.insertSpaceStations(spaceStations)
        }
    }
}