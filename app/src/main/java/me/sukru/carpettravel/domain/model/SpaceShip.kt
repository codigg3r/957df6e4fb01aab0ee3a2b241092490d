package me.sukru.carpettravel.domain.model

data class SpaceShip(
    val name: String = "",
    val strength: Float = 1F,
    val speed: Float = 1F,
    val capacity: Float = 1F,
    val health:Int = 100,
    val ugs: Double = 0.0,
    val eus: Double = 0.0,
    val ds: Double = 0.0,
    val dsTimer: Double = 0.0,
)