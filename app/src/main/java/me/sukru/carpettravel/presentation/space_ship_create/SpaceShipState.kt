package me.sukru.carpettravel.presentation.space_ship_create

data class SpaceShipState(
    val name: String = "",
    val speed: Float = 1F,
    val strength: Float = 1F,
    val capacity: Float = 1F,
    val remainingPoint: String = "12",
    val speedMaxValue:Float = 12F,
    val strengthMaxValue:Float = 12F,
    val capacityMaxValue:Float = 12F
)
