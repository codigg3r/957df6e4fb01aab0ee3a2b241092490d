package me.sukru.carpettravel.domain.use_case.space_ship_create

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import me.sukru.carpettravel.common.Resource
import me.sukru.carpettravel.data.local.CarpetTravelLocalDataSource
import me.sukru.carpettravel.data.toEntity
import me.sukru.carpettravel.domain.model.SpaceShip
import javax.inject.Inject

class UpsertSpaceShipUseCase @Inject constructor(
    private val localCarpetTravelDataSource: CarpetTravelLocalDataSource
) {
    suspend operator fun invoke(spaceShip: SpaceShip) {
        withContext(Dispatchers.IO) {
            localCarpetTravelDataSource.deleteAllSpaceShips()
            localCarpetTravelDataSource.insertSpaceShip(spaceShip.toEntity())
        }
    }
}