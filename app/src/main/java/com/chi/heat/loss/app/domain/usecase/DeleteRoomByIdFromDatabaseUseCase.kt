package com.chi.heat.loss.app.domain.usecase

import com.chi.heat.loss.app.domain.repository.HeatLossRepository
import com.chi.heat.loss.app.utils.emitFlow
import kotlinx.coroutines.flow.Flow

interface DeleteRoomByIdFromDatabaseUseCase {
    operator fun invoke(id: String): Flow<Unit>
}

class DeleteRoomByIdFromDatabaseUseCaseImpl(
    private val repository: HeatLossRepository
) : DeleteRoomByIdFromDatabaseUseCase {
    override fun invoke(id: String) = emitFlow {
        repository.deleteRoomById(id)
    }
}