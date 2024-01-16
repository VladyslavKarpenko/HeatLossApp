package com.chi.heat.loss.app.domain.usecase

import com.chi.heat.loss.app.domain.model.RoomDomain
import com.chi.heat.loss.app.domain.repository.HeatLossRepository
import kotlinx.coroutines.flow.Flow

interface GetAllRoomsByHouseIdFromDatabaseUseCase {
    operator fun invoke(houseId: String): Flow<List<RoomDomain>>
}

class GetAllRoomsByHouseIdFromDatabaseUseCaseImpl(
    private val repository: HeatLossRepository
) : GetAllRoomsByHouseIdFromDatabaseUseCase {
    override fun invoke(houseId: String) = repository.getAllRoomsByHouseId(houseId)
}