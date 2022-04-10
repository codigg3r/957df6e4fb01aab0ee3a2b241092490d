package me.sukru.carpettravel.domain.use_case.station

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.sukru.carpettravel.data.local.CarpetTravelLocalDataSource
import me.sukru.carpettravel.data.toEntity
import me.sukru.carpettravel.domain.model.SpaceShip
import me.sukru.carpettravel.domain.model.Station
import javax.inject.Inject

class TravelToStationUseCase @Inject constructor(
    private val localDataSource: CarpetTravelLocalDataSource
) {
    suspend operator fun invoke(
        spaceShip: SpaceShip,
        currentStation: Station,
        nextStation: Station
    ): TravelStatus {
        if (spaceShip.eus < nextStation.eus) return TravelStatus.Failure("Not enough EUS")
        if (spaceShip.ugs < nextStation.need) return TravelStatus.Failure("Not enough UGS")
        if (spaceShip.health <= 0) return TravelStatus.Failure("Space ship is destroyed")
        var newDsTimer = spaceShip.dsTimer - nextStation.eus
        val resetHealthFactor = if (newDsTimer <= 0) {
            ((-newDsTimer) / spaceShip.ds.div(1_000)).toInt().plus(1)
        } else {
            0
        }
        val newHealth = Math.max(0, (spaceShip.health - resetHealthFactor.times(10)))
        if (newHealth <= 0) return TravelStatus.Failure("Space ship is destroyed")
        if (resetHealthFactor > 0) {
            newDsTimer = spaceShip.ds.div(1_000).times(resetHealthFactor).plus(newDsTimer)
        }
        return withContext(Dispatchers.IO) {
            localDataSource.updateSpaceShip(
                spaceShip.copy(
                    eus = spaceShip.eus - nextStation.eus,
                    ugs = spaceShip.ugs - nextStation.need,
                    health = newHealth,
                    dsTimer = newDsTimer,
                ).toEntity()
            )
            localDataSource.updateSpaceStation(
                currentStation.copy(isCurrentStation = false).toEntity()
            )
            localDataSource.updateSpaceStation(
                nextStation.copy(
                    isCurrentStation = true,
                    isVisited = true
                ).toEntity()
            )
            if (localDataSource.isAllStationsVisited()) {
                TravelStatus.Finished
            } else {
                TravelStatus.Success(nextStation.name)
            }
        }
    }

    sealed class TravelStatus(val message: String) {
        data class Success(val stationName: String) : TravelStatus("Traveled to $stationName Station")
        data class Failure(val reason: String) : TravelStatus(reason)
        object Finished : TravelStatus("All stations are visited")
    }
}