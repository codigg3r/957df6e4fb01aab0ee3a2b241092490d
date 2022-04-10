package me.sukru.carpettravel.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.sukru.carpettravel.data.local.dto.SpaceShipEntity
import me.sukru.carpettravel.data.local.dto.SpaceStationEntity

@Dao
interface CarpetTravelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpaceShip(spaceShip: SpaceShipEntity)

    @Query("SELECT * FROM SpaceShipEntity LIMIT 1")
    fun getSpaceShip(): Flow<SpaceShipEntity>

    @Update
    fun updateSpaceShip(spaceShip: SpaceShipEntity): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpaceStations(vararg spaceShip: SpaceStationEntity)

    @Query("SELECT * FROM SpaceStationEntity")
    fun getSpaceStations():List<SpaceStationEntity>

    @Query("SELECT * FROM SpaceStationEntity WHERE name like '%' || :stationName || '%'")
    fun getSpaceStationsFlow(stationName: String): Flow<List<SpaceStationEntity>>

    @Query("SELECT * FROM SpaceStationEntity WHERE isFavorite like 1")
    fun getFavoriteStationsFlow(): Flow<List<SpaceStationEntity>>

    @Query("SELECT * FROM SpaceStationEntity WHERE isCurrentStation like 1")
    fun getCurrentStation(): Flow<SpaceStationEntity>

    @Update
    fun updateSpaceStation(spaceShip: SpaceStationEntity): Int

    @Query("SELECT COUNT(*) FROM SpaceStationEntity WHERE isVisited like 0")
    fun notYetVisitedStationsCount(): Int

}