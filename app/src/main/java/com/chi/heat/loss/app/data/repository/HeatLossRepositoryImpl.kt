package com.chi.heat.loss.app.data.repository

import com.chi.heat.loss.app.data.database.dao.HeatLossDao
import com.chi.heat.loss.app.data.mappers.toHouseDomain
import com.chi.heat.loss.app.data.mappers.toHouseEntity
import com.chi.heat.loss.app.data.mappers.toRoomDomain
import com.chi.heat.loss.app.data.mappers.toRoomEntity
import com.chi.heat.loss.app.domain.model.HouseDomain
import com.chi.heat.loss.app.domain.model.RoomDomain
import com.chi.heat.loss.app.domain.repository.HeatLossRepository
import kotlinx.coroutines.flow.map

class HeatLossRepositoryImpl(
    val heatLossDao: HeatLossDao
) : HeatLossRepository {

    override suspend fun addNewHouse(houseDomain: HouseDomain) =
        heatLossDao.insertHouses(houseDomain.toHouseEntity())

    override suspend fun addNewRoom(roomDomain: RoomDomain) =
        heatLossDao.insertRooms(roomDomain.toRoomEntity())

    override suspend fun deleteHouseById(id: String) = heatLossDao.deleteHouseWithRoomsByHouseId(id)
    override suspend fun deleteRoomById(id: String) = heatLossDao.deleteRoomById(id)

    override fun getAllHouses() =
        heatLossDao.getAllHouses().map { listOfHouses -> listOfHouses.map { it.toHouseDomain() } }

    override fun getHouseById(id: String) = heatLossDao.getHouseById(id).map { it.toHouseDomain() }

    override fun getAllRoomsByHouseId(houseId: String) =
        heatLossDao.getRoomsByHouseId(houseId)
            .map { listOfRooms -> listOfRooms.map { it.toRoomDomain() } }

}