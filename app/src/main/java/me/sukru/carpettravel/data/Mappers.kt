package me.sukru.carpettravel.data

import me.sukru.carpettravel.data.local.dto.SpaceShipEntity
import me.sukru.carpettravel.data.local.dto.SpaceStationEntity
import me.sukru.carpettravel.data.remote.dto.SpaceStation
import me.sukru.carpettravel.domain.model.SpaceShip
import me.sukru.carpettravel.domain.model.Station

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
        ugs = ugs,
        eus = eus,
        ds = ds,
        health = health,
        dsTimer = dsTimer,
    )
}

fun Station.toEntity(): SpaceStationEntity {
    return SpaceStationEntity(
        id = id,
        name = name,
        coordinateX = coordinateX,
        coordinateY = coordinateY,
        need = need,
        stock = stock,
        capacity = capacity,
        isFavorite = isFavorite,
        isVisited = isVisited,
    )
}

fun SpaceStationEntity.toDomain(): Station {
    return Station(
        id = id,
        name = name,
        coordinateX = coordinateX,
        coordinateY = coordinateY,
        capacity = capacity,
        need = need,
        stock = stock,
        isFavorite = isFavorite,
        isVisited = isVisited,
    )
}

fun SpaceShipEntity.toDomain(): SpaceShip {
    return SpaceShip(
        name = name,
        speed = speed.toFloat(),
        capacity = capacity.toFloat(),
        strength = strength.toFloat(),
        ugs = ugs,
        eus = eus,
        ds = ds,
        health = health,
        dsTimer = dsTimer,
    )
}