package me.sukru.carpettravel.data.local

import androidx.room.*
import me.sukru.carpettravel.data.local.dto.SpaceShipEntity
import me.sukru.carpettravel.data.local.dto.SpaceStationEntity

@Dao
interface CarpetTravelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpaceShip(spaceShip: SpaceShipEntity)

    @Query("DELETE FROM SpaceShipEntity")
    fun deleteSpaceShips()

    @Query("SELECT * FROM SpaceShipEntity LIMIT 1")
    fun getSpaceShip(): SpaceShipEntity

    @Update
    fun updateSpaceShip(spaceShip: SpaceShipEntity): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpaceStations(spaceShip: List<SpaceStationEntity>)

    @Query("SELECT * FROM SpaceStationEntity")
    fun getSpaceStations(): List<SpaceStationEntity?>

    @Update
    fun updateSpaceStation(spaceShip: SpaceStationEntity): Int

}