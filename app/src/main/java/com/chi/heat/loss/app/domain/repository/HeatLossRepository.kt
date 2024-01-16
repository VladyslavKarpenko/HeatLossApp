package com.chi.heat.loss.app.domain.repository

import com.chi.heat.loss.app.domain.model.HouseDomain
import com.chi.heat.loss.app.domain.model.RoomDomain
import kotlinx.coroutines.flow.Flow

interface HeatLossRepository {

    suspend fun addNewHouse(houseDomain: HouseDomain)

    suspend fun addNewRoom(roomDomain: RoomDomain)

    suspend fun deleteHouseById(id: String)

    suspend fun deleteRoomById(id: String)

    fun getAllHouses(): Flow<List<HouseDomain>>

    fun getHouseById(id: String): Flow<HouseDomain>

    fun getAllRoomsByHouseId(houseId: String): Flow<List<RoomDomain>>

}