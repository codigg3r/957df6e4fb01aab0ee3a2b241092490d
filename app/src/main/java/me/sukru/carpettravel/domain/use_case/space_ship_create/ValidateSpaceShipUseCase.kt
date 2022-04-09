package me.sukru.carpettravel.domain.use_case.space_ship_create

import me.sukru.carpettravel.domain.model.SpaceShip
import javax.inject.Inject

class ValidateSpaceShipUseCase @Inject constructor(){
    operator fun invoke(spaceShip: SpaceShip?): Boolean {
        spaceShip.apply {
            if (this == null) {
                return false
            }
            if (capacity <= 0) {
                return false
            }
            if (speed <= 0) {
                return false
            }
            if (strength <= 0) {
                return false
            }
            if (capacity + strength + speed > 15) {
                return false
            }
            return true
        }
    }
}