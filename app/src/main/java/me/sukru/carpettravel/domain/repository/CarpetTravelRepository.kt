package me.sukru.carpettravel.domain.repository

import kotlinx.coroutines.flow.Flow
import me.sukru.carpettravel.data.local.dto.SpaceShipEntity
import me.sukru.carpettravel.data.local.dto.SpaceStationEntity

interface CarpetTravelRepository {

    suspend fun getSpaceStations(): List<SpaceStationEntity>

    fun getSpaceShipFlow(): Flow<SpaceShipEntity>

    fun getSpaceShip(): SpaceShipEntity

    suspend fun insertSpaceShip(spaceShip: SpaceShipEntity)

    suspend fun insertSpaceStations(spaceStations: List<SpaceStationEntity>)

    suspend fun updateSpaceStation(spaceStation: SpaceStationEntity): Int

    suspend fun updateSpaceShip(spaceShip: SpaceShipEntity): Int

    suspend fun deleteAllSpaceStations()

    fun getSpaceStationsFlow(stationName: String): Flow<List<SpaceStationEntity>>

    fun getFavoriteStationsFlow(): Flow<List<SpaceStationEntity>>

    fun getCurrentStationFlow(): Flow<SpaceStationEntity?>

    fun isAllStationsVisited(): Boolean
}