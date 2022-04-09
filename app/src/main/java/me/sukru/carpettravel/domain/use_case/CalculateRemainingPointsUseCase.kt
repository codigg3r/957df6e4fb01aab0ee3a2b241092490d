package me.sukru.carpettravel.domain.use_case

import javax.inject.Inject

class CalculateRemainingPointsUseCase @Inject constructor() {
    operator fun invoke(
        speed: Float,
        strength: Float,
        capacity: Float,
        totalMaxValue: Float = 15F,
    ): RemainingPoints {
        val totalPoint = strength + speed + capacity
        val remainingPoints = totalMaxValue - totalPoint
        val speedMaxValue = speed + remainingPoints
        val strengthMaxValue = strength + remainingPoints
        val capacityMaxValue = capacity + remainingPoints
        return RemainingPoints(
            speedMaxValue = speedMaxValue,
            strengthMaxValue = strengthMaxValue,
            capacityMaxValue = capacityMaxValue,
            remainingTotalPoints = remainingPoints,
        )
    }

    data class RemainingPoints(
        val speedMaxValue: Float,
        val strengthMaxValue: Float,
        val capacityMaxValue: Float,
        val remainingTotalPoints: Float
    )
}