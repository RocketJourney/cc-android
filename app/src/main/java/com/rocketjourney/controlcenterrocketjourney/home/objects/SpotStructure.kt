package com.rocketjourney.controlcenterrocketjourney.home.objects

class SpotStructure(val itemType: SpotItemType, val spot: AccesibleSpot) {

    enum class SpotItemType {
        SPOT, DESCRIPTION, ALL_SPOTS
    }

}