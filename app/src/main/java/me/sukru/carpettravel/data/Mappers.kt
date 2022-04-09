package me.sukru.carpettravel.data

import me.sukru.carpettravel.data.local.dto.SpaceShipEntity
import me.sukru.carpettravel.data.local.dto.SpaceStationEntity
import me.sukru.carpettravel.data.remote.dto.SpaceStation
import me.sukru.carpettravel.domain.model.SpaceShip

fun SpaceStation.toEntity(): SpaceStationEntity {
    return SpaceStationEntity(
        name = name,
        coordinateX = coordinateX,
        coordinateY = coordinateY,
        capacity = capacity,
        need = need,
        stock = stock,
    )
}

fun SpaceShip.toEntity(): SpaceShipEntity {
     return SpaceShipEntity(
         name = name,
         speed = speed.toInt(),
         capacity = capacity.toInt(),
         strength = strength.toInt(),
     )
}