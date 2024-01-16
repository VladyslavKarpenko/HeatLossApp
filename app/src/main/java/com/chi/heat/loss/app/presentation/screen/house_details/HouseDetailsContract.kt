package com.chi.heat.loss.app.presentation.screen.house_details

import com.chi.heat.loss.app.base.UiEffect
import com.chi.heat.loss.app.base.UiEvent
import com.chi.heat.loss.app.base.UiState
import com.chi.heat.loss.app.presentation.model.HousePresentation
import com.chi.heat.loss.app.presentation.model.RoomPresentation
import com.chi.heat.loss.app.utils.RoomType

class HouseDetailsContract {

    data class State(
        val house: HousePresentation?,
        val listOfRooms: List<RoomPresentation>,
        val internalTemperature: Double,
        val ambientTemperature: Double,
        val recommendations: String,
        val isRecommendationsLoading: Boolean
    ) :
        UiState {
        companion object {
            fun initial() = State(
                house = null,
                listOfRooms = emptyList(),
                internalTemperature = 0.0,
                ambientTemperature = 0.0,
                recommendations = "",
                isRecommendationsLoading = false
            )
        }
    }

    sealed class Event : UiEvent {
        data class GetInfoAboutHouse(val houseId: String) : Event()
        data class AddNewRoom(val roomName: String) : Event()
        data class OnRoomClick(val roomType: RoomType) : Event()
        data class OnDeleteRoomButtonClick(val id: String) : Event()
        data class DeleteRoom(val id: String) : Event()
        data class OnInternalTemperatureChange(val value: Double) : Event()
        data class OnAmbientTemperatureChange(val value: Double) : Event()
        data class GetRecommendationsFromChatGPT(val request: String) : Event()
    }

    sealed class Effect : UiEffect {
        data class OpenDeleteDialog(val id: String) : Effect()
        data class NavigateToAddNewRoomScreen(val roomType: RoomType) : Effect()
    }
}