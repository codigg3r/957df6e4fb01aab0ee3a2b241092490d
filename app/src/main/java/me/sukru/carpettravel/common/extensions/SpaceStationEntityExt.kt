package me.sukru.carpettravel.common.extensions

import me.sukru.carpettravel.data.local.dto.SpaceStationEntity
import me.sukru.carpettravel.domain.model.Station
import kotlin.math.pow

/**
 * currentStation is the current station the user is at.
 * if you want to calculate the distance between origin set currentStation to null.
 */
fun SpaceStationEntity.calculateEUS(currentStation: Station? = null): Double {
    return if (currentStation == null) {
        val x = this.coordinateX
        val y = this.coordinateY
        (x * x + y * y).pow(0.5)
    } else {
        val x = this.coordinateX - currentStation.coordinateX
        val y = this.coordinateY - currentStation.coordinateY
        (x * x + y * y).pow(0.5)
    }
}