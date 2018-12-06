package com.rocketjourney.controlcenterrocketjourney.home.objects

import java.io.Serializable

class SpotStructure(val itemType: SpotItemType, val spot: AccesibleSpot?, var checked: Boolean = false) : Serializable {

    enum class SpotItemType {
        SPOT, DESCRIPTION, ALL_SPOTS
    }

}