package com.chi.heat.loss.app.presentation.screen.add_new_room

import com.chi.heat.loss.app.base.UiEffect
import com.chi.heat.loss.app.base.UiEvent
import com.chi.heat.loss.app.base.UiState
import com.chi.heat.loss.app.presentation.model.RoomComponent

class AddNewRoomContract {

    data class State(val list: String) : UiState {
        companion object {
            fun initial() = State(list = "")
        }
    }

    sealed class Event : UiEvent {
        data class AddNewHouse(
            val name: String,
            val list: List<RoomComponent>,
            val houseId: String
        ) :
            Event()
    }

    sealed class Effect : UiEffect {
        object NavigateToHouseDetails : Effect()
    }
}