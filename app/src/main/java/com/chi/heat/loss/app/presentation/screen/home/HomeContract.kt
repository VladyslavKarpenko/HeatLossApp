package com.chi.heat.loss.app.presentation.screen.home

import com.chi.heat.loss.app.base.UiEffect
import com.chi.heat.loss.app.base.UiEvent
import com.chi.heat.loss.app.base.UiState
import com.chi.heat.loss.app.presentation.model.HousePresentation

class HomeContract {

    data class State(val listOfHouses: List<HousePresentation>) : UiState {
        companion object {
            fun initial() = State(listOfHouses = emptyList())
        }
    }

    sealed class Event : UiEvent {
        object OnAddNewHouseButtonClick : Event()
        data class OnDeleteHouseButtonClick(val id: String) : Event()
        data class OnItemClick(val id: String) : Event()
        data class DeleteHouse(val id: String) : Event()
        object GetListOfHouses : Event()
    }

    sealed class Effect : UiEffect {
        data class OpenDeleteDialog(val id: String) : Effect()
        object NavigateToAddNewHouseDialog : Effect()
        data class NavigateToHouseDetailsScreen(val houseId: String) : Effect()

    }
}