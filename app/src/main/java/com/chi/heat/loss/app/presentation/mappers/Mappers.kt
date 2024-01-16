package com.chi.heat.loss.app.presentation.mappers

import com.chi.heat.loss.app.domain.model.HouseDomain
import com.chi.heat.loss.app.domain.model.RoomDomain
import com.chi.heat.loss.app.presentation.model.HousePresentation
import com.chi.heat.loss.app.presentation.model.RoomPresentation

fun HouseDomain.toHousePresentation() = HousePresentation(
    id = id,
    name = name,
    picture = picture
)

fun HousePresentation.toHouseDomain() = HouseDomain(
    id = id,
    name = name,
    picture = picture
)

fun RoomDomain.toRoomPresentation() = RoomPresentation(
    id = id,
    name = name,
    heatLoss = heatLoss,
    houseId = houseId
)

fun RoomPresentation.toRoomDomain() = RoomDomain(
    id = id,
    name = name,
    heatLoss = heatLoss,
    houseId = houseId
)