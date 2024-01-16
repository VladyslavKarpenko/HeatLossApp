package com.chi.heat.loss.app.domain.usecase

import com.chi.heat.loss.app.domain.model.HouseDomain
import com.chi.heat.loss.app.domain.repository.HeatLossRepository
import com.chi.heat.loss.app.utils.emitFlow
import kotlinx.coroutines.flow.Flow

interface AddNewHouseToDatabaseUseCase {
    operator fun invoke(houseDomain: HouseDomain): Flow<Unit>
}

class AddNewHouseToDatabaseUseCaseImpl(
    private val repository: HeatLossRepository
) : AddNewHouseToDatabaseUseCase {
    override fun invoke(houseDomain: HouseDomain) = emitFlow {
        repository.addNewHouse(houseDomain)
    }
}