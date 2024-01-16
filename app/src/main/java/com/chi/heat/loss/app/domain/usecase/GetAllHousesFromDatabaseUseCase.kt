package com.chi.heat.loss.app.domain.usecase

import com.chi.heat.loss.app.domain.model.HouseDomain
import com.chi.heat.loss.app.domain.repository.HeatLossRepository
import kotlinx.coroutines.flow.Flow

interface GetAllHousesFromDatabaseUseCase {
    operator fun invoke(): Flow<List<HouseDomain>>
}

class GetAllHousesFromDatabaseUseCaseImpl(
    private val repository: HeatLossRepository
) : GetAllHousesFromDatabaseUseCase {
    override fun invoke() = repository.getAllHouses()
}