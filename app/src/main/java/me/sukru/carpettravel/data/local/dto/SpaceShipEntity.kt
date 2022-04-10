package me.sukru.carpettravel.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SpaceShipEntity(
    @PrimaryKey(autoGenerate = false)
    val _id: Long = 0L,
    val name: String?,
    val strength: Int?,
    val speed: Int?,
    val capacity: Int?,
    val health:Int?,
    val ugs: Double?,
    val eus: Double?,
    val ds: Double?,
    val dsTimer: Double?,
    val ugsInitial: Double?,
    val eusInitial: Double?,
    val dsInitial: Double?,
    val dsTimerInitial: Double?,
)