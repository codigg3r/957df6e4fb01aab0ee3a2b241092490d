package me.sukru.carpettravel.domain.use_case.station

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import me.sukru.carpettravel.common.Resource
import me.sukru.carpettravel.common.extensions.calculateEUS
import me.sukru.carpettravel.data.local.CarpetTravelLocalDataSource
import me.sukru.carpettravel.data.local.dto.SpaceStationEntity
import me.sukru.carpettravel.data.remote.CarpetTravelRemoteDataSource
import me.sukru.carpettravel.data.toDomain
import me.sukru.carpettravel.domain.model.Station
import javax.inject.Inject
import kotlin.math.pow

class GetStationListUseCase @Inject constructor(
    private val remoteDataSource: CarpetTravelRemoteDataSource,
    private val localDataSource: CarpetTravelLocalDataSource,
) {
    /**
     * Get station list from remote data source
     * @return [Resource] with [List] of [Station]
     * currentStation is for calculating EUS
     */
    operator fun invoke(stationName: String = "") = flow<Resource<List<Station>>> {
        emit(Resource.Loading())
        var spaceStationList = localDataSource.getSpaceStations()
        var hasError = false
        if (spaceStationList.isEmpty()) {
            try {
                spaceStationList = remoteDataSource.getSpaceStations()
                localDataSource.insertSpaceStations(spaceStationList.map { if (it.name == "Dünya") it.copy(isCurrentStation = true, isVisited = true) else it })
            } catch (e: Exception) {
                hasError = true
            }
        }
        if (hasError) {
            emit(Resource.Error("Network error happened"))
        } else {
           localDataSource.getSpaceStationsFlow(stationName).collect {
               val currentStation = it.firstOrNull { it.isCurrentStation }
               emit(Resource.Success(it.map { spaceStation ->
                   spaceStation.toDomain().copy(eus = spaceStation.calculateEUS(currentStation?.toDomain()))
               }))
           }
        }

    }.flowOn(Dispatchers.IO)
}