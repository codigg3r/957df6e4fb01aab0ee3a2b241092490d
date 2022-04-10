package me.sukru.carpettravel.domain.use_case.station

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import me.sukru.carpettravel.common.Resource
import me.sukru.carpettravel.data.local.CarpetTravelLocalDataSource
import me.sukru.carpettravel.data.toDomain
import me.sukru.carpettravel.domain.model.Station
import javax.inject.Inject

class GetCurrentStationUseCase @Inject constructor(
    private val localDataSource: CarpetTravelLocalDataSource,
) {
    operator fun invoke() = flow<Resource<Station>> {
        emit(Resource.Loading())
        localDataSource.getCurrentStationFlow().collect {
            if (it != null) {
                emit(Resource.Success(it.toDomain()))
            }
        }
    }
}