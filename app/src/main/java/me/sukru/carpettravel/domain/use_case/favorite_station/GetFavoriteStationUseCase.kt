package me.sukru.carpettravel.domain.use_case.favorite_station

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import me.sukru.carpettravel.common.Resource
import me.sukru.carpettravel.common.extensions.calculateEUS
import me.sukru.carpettravel.data.local.CarpetTravelLocalRepositoryImpl
import me.sukru.carpettravel.data.toDomain
import me.sukru.carpettravel.domain.model.Station
import javax.inject.Inject

class GetFavoriteStationUseCase @Inject constructor(
    private val localRepositoryImpl: CarpetTravelLocalRepositoryImpl
) {
    operator fun invoke() = flow<Resource<List<Station>>> {
        emit(Resource.Loading())
        localRepositoryImpl.getFavoriteStationsFlow().collect {
            emit(Resource.Success(it.map { spaceStation ->
                spaceStation.toDomain().copy(eus = spaceStation.calculateEUS())
            }))
        }
    }
}