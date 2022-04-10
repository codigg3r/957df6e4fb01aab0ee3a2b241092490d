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
        ugsInitial = ugsInitial,
        eusInitial = eusInitial,
        dsInitial = dsInitial,
        dsTimerInitial = dsTimerInitial,
    )
}

fun Station.toEntity(): SpaceStationEntity {
    return SpaceStationEntity(
        _id = id,
        name = name,
        coordinateX = coordinateX,
        coordinateY = coordinateY,
        need = need,
        stock = stock,
        capacity = capacity,
        isFavorite = isFavorite,
        isVisited = isVisited,
        isCurrentStation = isCurrentStation,
    )
}

fun SpaceStationEntity.toDomain(): Station {
    return Station(
        id = _id,
        name = name,
        coordinateX = coordinateX,
        coordinateY = coordinateY,
        capacity = capacity,
        need = need,
        stock = stock,
        isFavorite = isFavorite,
        isVisited = isVisited,
        isCurrentStation = isCurrentStation,
    )
}

fun SpaceShipEntity.toDomain(): SpaceShip {
    return SpaceShip(
        name = name ?: "",
        speed = speed?.toFloat() ?: 0F,
        capacity = capacity?.toFloat() ?: 0F,
        strength = strength?.toFloat() ?: 0F,
        ugs = ugs ?: 0.0,
        eus = eus ?: 0.0,
        ds = ds ?: 0.0,
        health = health ?: 0,
        dsTimer = dsTimer ?: 0.0,
        ugsInitial = ugsInitial ?: 0.0,
        eusInitial = eusInitial ?: 0.0,
        dsInitial = dsInitial ?: 0.0,
        dsTimerInitial = dsTimerInitial ?: 0.0,
    )
}