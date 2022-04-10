package me.sukru.carpettravel.domain.use_case.station

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.sukru.carpettravel.common.Resource
import me.sukru.carpettravel.data.local.CarpetTravelLocalDataSource
import me.sukru.carpettravel.data.toDomain
import me.sukru.carpettravel.domain.model.SpaceShip
import javax.inject.Inject

class GetSpaceShipUseCase @Inject constructor(
    private val localDataSource: CarpetTravelLocalDataSource
) {
    operator fun invoke() = flow<Resource<SpaceShip>> {
        emit(Resource.Loading())
        localDataSource.getSpaceShipFlow().collect {
            try {
                emit(Resource.Success(it.toDomain()))
            } catch (e: Exception) {
                emit(Resource.Error("Error getting space ship"))
            }
        }
    }.flowOn(Dispatchers.IO)
}