package com.chi.heat.loss.app.presentation.screen.add_new_room

import com.chi.heat.loss.app.base.BaseViewModel
import com.chi.heat.loss.app.domain.usecase.AddNewRoomToDatabaseUseCase
import com.chi.heat.loss.app.presentation.mappers.toRoomDomain
import com.chi.heat.loss.app.presentation.model.RoomPresentation
import com.chi.heat.loss.app.utils.subscribe
import kotlinx.coroutines.launch

class AddNewRoomViewModel(
    private val addNewRoomToDatabaseUseCase: AddNewRoomToDatabaseUseCase,
) : BaseViewModel<AddNewRoomContract.State, AddNewRoomContract.Event, AddNewRoomContract.Effect>() {

    override fun createInitialState() = AddNewRoomContract.State.initial()

    override fun handleEvent(event: AddNewRoomContract.Event) {
        when (event) {
            is AddNewRoomContract.Event.AddNewHouse -> {
                var roomHeatLoss = 0.0
                event.list.forEach {
                    val componentHeatLoss = it.area * it.insulated
                    roomHeatLoss += componentHeatLoss
                }
                addRoom(event.name, roomHeatLoss, event.houseId)
            }
        }
    }

    private fun addRoom(name: String, heatLoss: Double, houseId: String) = launch {
        addNewRoomToDatabaseUseCase.invoke(
            RoomPresentation(
                id = System.currentTimeMillis().toString(),
                name = name,
                heatLoss = heatLoss,
                houseId = houseId
            ).toRoomDomain()
        ).subscribe(
            success = {
                setEffect {
                    AddNewRoomContract.Effect.NavigateToHouseDetails
                }
            }
        )
    }
}