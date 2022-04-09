package me.sukru.carpettravel.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SpaceShipEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val strength: Int,
    val speed: Int,
    val capacity: Int,
    val health:Int,
    val ugs: Double,
    val eus: Double,
    val ds: Double,
    val dsTimer: Double,
)