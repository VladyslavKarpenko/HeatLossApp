package com.chi.heat.loss.app.domain.usecase

import com.chi.heat.loss.app.domain.model.HouseDomain
import com.chi.heat.loss.app.domain.repository.HeatLossRepository
import kotlinx.coroutines.flow.Flow

interface GetHouseByIdFromDatabaseUseCase {
    operator fun invoke(houseId: String): Flow<HouseDomain>
}

class GetHouseByIdFromDatabaseUseCaseImpl(
    private val repository: HeatLossRepository
) : GetHouseByIdFromDatabaseUseCase {
    override fun invoke(houseId: String) = repository.getHouseById(houseId)
}