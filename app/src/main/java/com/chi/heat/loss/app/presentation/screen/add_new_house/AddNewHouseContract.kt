package com.chi.heat.loss.app.presentation.screen.add_new_house

import com.chi.heat.loss.app.R
import com.chi.heat.loss.app.base.UiEffect
import com.chi.heat.loss.app.base.UiEvent
import com.chi.heat.loss.app.base.UiState
import com.chi.heat.loss.app.presentation.model.HouseAvatarPresentation
import com.chi.heat.loss.app.presentation.model.HousePresentation

class AddNewHouseContract {

    data class State(val listOfAvatar: List<HouseAvatarPresentation>) : UiState {
        companion object {
            fun initial() = State(listOfAvatar = listOfAvatar)
            private val listOfAvatar = listOf(
                HouseAvatarPresentation(
                    resourceId = R.drawable.ic_house_1,
                    isSelected = true
                ),
                HouseAvatarPresentation(
                    resourceId = R.drawable.ic_house_2,
                ),
                HouseAvatarPresentation(
                    resourceId = R.drawable.ic_house_3,
                ),
                HouseAvatarPresentation(
                    resourceId = R.drawable.ic_house_4,
                ),
                HouseAvatarPresentation(
                    resourceId = R.drawable.ic_house_5,
                )
            )
        }
    }

    sealed class Event : UiEvent {
        data class AddNewHouse(val house: HousePresentation) : Event()
    }

    sealed class Effect : UiEffect {
        data class NavigateToHouseDetailsScreen(val houseId: String) : Effect()
    }
}