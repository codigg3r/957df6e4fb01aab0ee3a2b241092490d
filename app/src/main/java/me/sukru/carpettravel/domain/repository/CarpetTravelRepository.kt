package me.sukru.carpettravel.domain.repository

import me.sukru.carpettravel.data.local.dto.SpaceShipEntity
import me.sukru.carpettravel.data.local.dto.SpaceStationEntity

interface CarpetTravelRepository {

    suspend fun getSpaceStations(): List<SpaceStationEntity?>

    suspend fun getSpaceShip(): SpaceShipEntity

    suspend fun insertSpaceShip(spaceShip: SpaceShipEntity)

    suspend fun insertSpaceStations(spaceStations: List<SpaceStationEntity>)

    suspend fun updateSpaceStation(spaceStation: SpaceStationEntity): Int

    suspend fun updateSpaceShip(spaceShip: SpaceShipEntity): Int

    suspend fun deleteAllSpaceShips()
}