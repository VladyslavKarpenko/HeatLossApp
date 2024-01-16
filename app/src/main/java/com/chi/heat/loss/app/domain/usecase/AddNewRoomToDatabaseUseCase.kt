package com.chi.heat.loss.app.domain.usecase

import com.chi.heat.loss.app.domain.model.RoomDomain
import com.chi.heat.loss.app.domain.repository.HeatLossRepository
import com.chi.heat.loss.app.utils.emitFlow
import kotlinx.coroutines.flow.Flow

interface AddNewRoomToDatabaseUseCase {
    operator fun invoke(roomDomain: RoomDomain): Flow<Unit>
}

class AddNewRoomToDatabaseUseCaseImpl(
    private val repository: HeatLossRepository
) : AddNewRoomToDatabaseUseCase {
    override fun invoke(roomDomain: RoomDomain) = emitFlow {
        repository.addNewRoom(roomDomain)
    }
}