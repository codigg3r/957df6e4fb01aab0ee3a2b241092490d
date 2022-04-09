package me.sukru.carpettravel.domain.model

data class Station(
    val id: Long,
    val name: String,
    val coordinateX: Double,
    val coordinateY: Double,
    val need: Int,
    val stock: Int,
    val capacity: Int,
    val isFavorite: Boolean = false,
    val isVisited: Boolean = false,
    val eus: Double = 0.0,
)