package me.sukru.carpettravel.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import me.sukru.carpettravel.data.local.dto.SpaceShipEntity
import me.sukru.carpettravel.data.local.dto.SpaceStationEntity

@Database(entities = [SpaceShipEntity::class, SpaceStationEntity::class], version = 2, exportSchema = true)
abstract class CarpetTravelDatabase: RoomDatabase() {
    abstract fun carpetTravelDao(): CarpetTravelDao
}