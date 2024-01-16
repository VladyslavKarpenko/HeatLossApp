package com.chi.heat.loss.app.presentation.screen.add_new_house

import com.chi.heat.loss.app.base.BaseViewModel
import com.chi.heat.loss.app.domain.usecase.AddNewHouseToDatabaseUseCase
import com.chi.heat.loss.app.presentation.mappers.toHouseDomain
import com.chi.heat.loss.app.presentation.model.HousePresentation
import com.chi.heat.loss.app.utils.subscribe
import kotlinx.coroutines.launch

class AddNewHouseViewModel(
    private val addNewHouseToDatabaseUseCase: AddNewHouseToDatabaseUseCase
) : BaseViewModel<AddNewHouseContract.State, AddNewHouseContract.Event, AddNewHouseContract.Effect>() {

    override fun createInitialState() = AddNewHouseContract.State.initial()

    override fun handleEvent(event: AddNewHouseContract.Event) {
        when (event) {
            is AddNewHouseContract.Event.AddNewHouse -> addHouse(event.house)
        }
    }

    private fun addHouse(housePresentation: HousePresentation) = launch {
        addNewHouseToDatabaseUseCase.invoke(housePresentation.toHouseDomain()).subscribe(
            success = {
                setEffect {
                    AddNewHouseContract.Effect.NavigateToHouseDetailsScreen(housePresentation.id)
                }
            }
        )
    }
}