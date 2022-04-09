package me.sukru.carpettravel.data.remote.dto


import com.google.gson.annotations.SerializedName

data class SpaceStation(
    @SerializedName("capacity")
    val capacity: Int = 0,
    @SerializedName("coordinateX")
    val coordinateX: Double = 0.0,
    @SerializedName("coordinateY")
    val coordinateY: Double = 0.0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("need")
    val need: Int = 0,
    @SerializedName("stock")
    val stock: Int = 0
)