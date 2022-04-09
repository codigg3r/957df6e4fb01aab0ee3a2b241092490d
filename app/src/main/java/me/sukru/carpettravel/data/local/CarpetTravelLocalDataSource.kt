package me.sukru.carpettravel.data.local

import kotlinx.coroutines.flow.Flow
import me.sukru.carpettravel.data.local.dto.SpaceShipEntity
import me.sukru.carpettravel.data.local.dto.SpaceStationEntity
import me.sukru.carpettravel.domain.repository.CarpetTravelRepository
import javax.inject.Inject

class CarpetTravelLocalDataSource @Inject constructor(
    private val carpetTravelDao: CarpetTravelDao
) : CarpetTravelRepository {
    override suspend fun getSpaceStations(): List<SpaceStationEntity> {
        return carpetTravelDao.getSpaceStations()
    }

    override suspend fun getSpaceShip(): SpaceShipEntity {
        return carpetTravelDao.getSpaceShip()
    }

    override suspend fun insertSpaceShip(spaceShip: SpaceShipEntity) {
        carpetTravelDao.insertSpaceShip(spaceShip)
    }

    override suspend fun insertSpaceStations(spaceStations: List<SpaceStationEntity>) {
        carpetTravelDao.insertSpaceStations(*spaceStations.toTypedArray())
    }

    override suspend fun updateSpaceStation(spaceStation: SpaceStationEntity): Int {
        return carpetTravelDao.updateSpaceStation(spaceStation)
    }

    override suspend fun updateSpaceShip(spaceShip: SpaceShipEntity): Int {
        return carpetTravelDao.updateSpaceShip(spaceShip)
    }

    override suspend fun deleteAllSpaceShips() {
        carpetTravelDao.deleteSpaceShips()
    }

    override fun getSpaceStationsFlow(stationName: String, isOnlyFavorite: Boolean): Flow<List<SpaceStationEntity>> {
        if(isOnlyFavorite) {
            return carpetTravelDao.getFavoriteStationsFlow()
        }
        return carpetTravelDao.getSpaceStationsFlow(stationName)
    }

}