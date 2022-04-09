package me.sukru.carpettravel.data.remote

import me.sukru.carpettravel.data.remote.dto.SpaceStation
import retrofit2.http.GET

interface SpaceStationApi {
    @GET("v3/e7211664-cbb6-4357-9c9d-f12bf8bab2e2")
    suspend fun getSpaceStation(): List<SpaceStation>
}