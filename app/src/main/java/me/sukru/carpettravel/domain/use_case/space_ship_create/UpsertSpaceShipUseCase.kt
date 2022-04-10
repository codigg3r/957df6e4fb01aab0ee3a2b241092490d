package me.sukru.carpettravel.domain.use_case.space_ship_create

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import me.sukru.carpettravel.common.Resource
import me.sukru.carpettravel.data.local.CarpetTravelLocalDataSource
import me.sukru.carpettravel.data.toEntity
import me.sukru.carpettravel.domain.model.SpaceShip
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class UpsertSpaceShipUseCase @Inject constructor(
    private val localCarpetTravelDataSource: CarpetTravelLocalDataSource
) {
    suspend operator fun invoke(spaceShip: SpaceShip) {
        withContext(Dispatchers.IO) {
            val spaceShipWithValues = spaceShip.copy(
                ugs = spaceShip.capacity.times(10_000.0),
                eus = spaceShip.speed.times(20.0),
                ds = spaceShip.strength.times(10_000.0),
                dsTimer = spaceShip.strength.times(10.0)
            )
            localCarpetTravelDataSource.insertSpaceShip(spaceShipWithValues.toEntity())
        }
    }
}