package me.sukru.carpettravel.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SpaceStationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val coordinateX: Double,
    val coordinateY: Double,
    val need: Int,
    val stock: Int,
    val capacity: Int,
    val isFavorite: Boolean = false,
    val isVisited: Boolean = false,
    val isCurrentStation: Boolean = false,
)
