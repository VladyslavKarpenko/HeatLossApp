package com.chi.heat.loss.app.presentation.screen.house_details

import androidx.lifecycle.viewModelScope
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.chi.heat.loss.app.base.BaseViewModel
import com.chi.heat.loss.app.domain.usecase.AddNewRoomToDatabaseUseCase
import com.chi.heat.loss.app.domain.usecase.DeleteRoomByIdFromDatabaseUseCase
import com.chi.heat.loss.app.domain.usecase.GetAllRoomsByHouseIdFromDatabaseUseCase
import com.chi.heat.loss.app.domain.usecase.GetHouseByIdFromDatabaseUseCase
import com.chi.heat.loss.app.presentation.mappers.toHousePresentation
import com.chi.heat.loss.app.presentation.mappers.toRoomDomain
import com.chi.heat.loss.app.presentation.mappers.toRoomPresentation
import com.chi.heat.loss.app.presentation.model.RoomPresentation
import com.chi.heat.loss.app.utils.subscribe
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HouseDetailsViewModel(
    private val getHouseByIdFromDatabaseUseCase: GetHouseByIdFromDatabaseUseCase,
    private val getAllRoomsByHouseIdFromDatabaseUseCase: GetAllRoomsByHouseIdFromDatabaseUseCase,
    private val addNewRoomToDatabaseUseCase: AddNewRoomToDatabaseUseCase,
    private val deleteRoomByIdFromDatabaseUseCase: DeleteRoomByIdFromDatabaseUseCase
) :
    BaseViewModel<HouseDetailsContract.State, HouseDetailsContract.Event, HouseDetailsContract.Effect>() {

    override fun createInitialState() = HouseDetailsContract.State.initial()

    override fun handleEvent(event: HouseDetailsContract.Event) {
        when (event) {
            is HouseDetailsContract.Event.GetInfoAboutHouse -> getInfoAboutHouse(event.houseId)
            is HouseDetailsContract.Event.AddNewRoom -> addRoom(event.roomName)
            is HouseDetailsContract.Event.DeleteRoom -> deleteRoom(event.id)
            is HouseDetailsContract.Event.OnDeleteRoomButtonClick -> setEffect {
                HouseDetailsContract.Effect.OpenDeleteDialog(
                    event.id
                )
            }

            is HouseDetailsContract.Event.OnRoomClick -> setEffect {
                HouseDetailsContract.Effect.NavigateToAddNewRoomScreen(
                    event.roomType
                )
            }

            is HouseDetailsContract.Event.OnAmbientTemperatureChange -> setState {
                copy(
                    ambientTemperature = event.value
                )
            }

            is HouseDetailsContract.Event.OnInternalTemperatureChange -> setState {
                copy(
                    internalTemperature = event.value
                )
            }

            is HouseDetailsContract.Event.GetRecommendationsFromChatGPT -> getGPTResponse(event.request)
        }
    }

    private fun getHouseById(id: String) = launch {
        getHouseByIdFromDatabaseUseCase.invoke(id).subscribe(
            success = {
                setState { copy(house = it.toHousePresentation()) }
            }
        )
    }

    private fun getAllRoomsByHouseId(houseId: String) = launch {
        getAllRoomsByHouseIdFromDatabaseUseCase.invoke(houseId).subscribe(
            success = { listOfRooms ->
                setState {
                    copy(listOfRooms = listOfRooms.map { it.toRoomPresentation() })
                }
            }
        )
    }

    private fun getInfoAboutHouse(houseId: String) {
        getHouseById(houseId)
        getAllRoomsByHouseId(houseId)
    }

    private fun addRoom(room: String) = launch {
        addNewRoomToDatabaseUseCase.invoke(
            RoomPresentation(
                id = System.currentTimeMillis().toString(),
                name = room,
                heatLoss = 0.0,
                houseId = currentState.house?.id ?: ""
            ).toRoomDomain()
        ).collect()
    }

    private fun deleteRoom(id: String) = launch {
        deleteRoomByIdFromDatabaseUseCase.invoke(id).collect()
    }

    @OptIn(BetaOpenAI::class)
    fun getGPTResponse(request: String) = viewModelScope.launch {
        setState { copy(isRecommendationsLoading = true, recommendations = "") }

        val openAI = OpenAI(CHAT_GPT_API_KEY)

        try {
            val chatCompletionRequest = ChatCompletionRequest(
                model = ModelId("gpt-3.5-turbo"),
                messages = listOf(
                    ChatMessage(
                        role = ChatRole.User,
                        content = request
                    )
                )
            )

            val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)

            val response = completion.choices.first().message?.content

            setState { copy(recommendations = response ?: "", isRecommendationsLoading = false) }
        } catch (e: Exception) {
            setState {
                copy(
                    recommendations = "ERROR: ${e.message ?: ""}",
                    isRecommendationsLoading = false
                )
            }
        }
    }

    companion object {
        const val CHAT_GPT_API_KEY = "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"
    }
}