package me.sukru.carpettravel.data.remote

import kotlinx.coroutines.flow.Flow
import me.sukru.carpettravel.data.local.dto.SpaceShipEntity
import me.sukru.carpettravel.data.local.dto.SpaceStationEntity
import me.sukru.carpettravel.data.toEntity
import me.sukru.carpettravel.domain.repository.CarpetTravelRepository
import javax.inject.Inject

class CarpetTravelRemoteDataSource @Inject constructor(
    private val api: SpaceStationApi
) : CarpetTravelRepository {
    override suspend fun getSpaceStations(): List<SpaceStationEntity> {
        return api.getSpaceStation().map { it.toEntity() }
    }

    override fun getSpaceShip(): Flow<SpaceShipEntity> {
        throw NotImplementedError()
    }

    override suspend fun insertSpaceShip(spaceShip: SpaceShipEntity) {
        throw NotImplementedError()
    }

    override suspend fun insertSpaceStations(spaceStations: List<SpaceStationEntity>) {
        throw NotImplementedError()
    }

    override suspend fun updateSpaceStation(spaceStation: SpaceStationEntity): Int {
        throw NotImplementedError()
    }

    override suspend fun updateSpaceShip(spaceShip: SpaceShipEntity): Int {
        throw NotImplementedError()
    }

    override suspend fun deleteAllSpaceShips() {
        throw NotImplementedError()
    }

    override fun getSpaceStationsFlow(stationName: String): Flow<List<SpaceStationEntity>> {
        throw NotImplementedError()
    }

    override fun getFavoriteStationsFlow(): Flow<List<SpaceStationEntity>> {
        throw NotImplementedError()
    }

    override fun getCurrentStationFlow(): Flow<SpaceStationEntity?> {
        throw NotImplementedError()
    }

    override fun isAllStationsVisited(): Boolean {
        throw NotImplementedError()
    }
}