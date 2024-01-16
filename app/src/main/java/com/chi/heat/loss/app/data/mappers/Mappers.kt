package com.chi.heat.loss.app.data.mappers

import com.chi.heat.loss.app.data.database.model.HouseEntity
import com.chi.heat.loss.app.data.database.model.RoomEntity
import com.chi.heat.loss.app.domain.model.HouseDomain
import com.chi.heat.loss.app.domain.model.RoomDomain

fun HouseEntity.toHouseDomain() = HouseDomain(
    id = id,
    name = name,
    picture = picture
)

fun HouseDomain.toHouseEntity() = HouseEntity(
    id = id,
    name = name,
    picture = picture
)

fun RoomEntity.toRoomDomain() = RoomDomain(
    id = id,
    name = name,
    heatLoss = heatLoss,
    houseId = houseId
)

fun RoomDomain.toRoomEntity() = RoomEntity(
    id = id,
    name = name,
    heatLoss = heatLoss,
    houseId = houseId
)