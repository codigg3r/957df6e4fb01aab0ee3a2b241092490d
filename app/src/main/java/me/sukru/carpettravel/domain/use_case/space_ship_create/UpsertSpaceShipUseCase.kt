package me.sukru.carpettravel.domain.use_case.space_ship_create

import me.sukru.carpettravel.data.local.CarpetTravelLocalRepositoryImpl
import me.sukru.carpettravel.data.toEntity
import me.sukru.carpettravel.domain.model.SpaceShip
import javax.inject.Inject

class UpsertSpaceShipUseCase @Inject constructor(
    private val localRepositoryImpl: CarpetTravelLocalRepositoryImpl
) {
    suspend operator fun invoke(spaceShip: SpaceShip) {
        val spaceShipWithValues = spaceShip.copy(
            ugs = spaceShip.capacity.times(10_000.0),
            eus = spaceShip.speed.times(20.0),
            ds = spaceShip.strength.times(10_000.0),
            dsTimer = spaceShip.strength.times(10.0),
            ugsInitial = spaceShip.capacity.times(10_000.0),
            eusInitial = spaceShip.speed.times(20.0),
            dsInitial = spaceShip.strength.times(10_000.0),
            dsTimerInitial = spaceShip.strength.times(10.0),
        )
        localRepositoryImpl.insertSpaceShip(spaceShipWithValues.toEntity())
    }
}