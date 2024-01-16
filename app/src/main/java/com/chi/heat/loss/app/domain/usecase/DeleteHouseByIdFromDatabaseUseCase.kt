package com.chi.heat.loss.app.domain.usecase

import com.chi.heat.loss.app.domain.repository.HeatLossRepository
import com.chi.heat.loss.app.utils.emitFlow
import kotlinx.coroutines.flow.Flow

interface DeleteHouseByIdFromDatabaseUseCase {
    operator fun invoke(id: String): Flow<Unit>
}

class DeleteHouseByIdFromDatabaseUseCaseImpl(
    private val repository: HeatLossRepository
) : DeleteHouseByIdFromDatabaseUseCase {
    override fun invoke(id: String) = emitFlow {
        repository.deleteHouseById(id)
    }
}