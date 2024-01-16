package com.chi.heat.loss.app.presentation.screen.home

import com.chi.heat.loss.app.base.BaseViewModel
import com.chi.heat.loss.app.domain.usecase.DeleteHouseByIdFromDatabaseUseCase
import com.chi.heat.loss.app.domain.usecase.GetAllHousesFromDatabaseUseCase
import com.chi.heat.loss.app.presentation.mappers.toHousePresentation
import com.chi.heat.loss.app.utils.subscribe
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val deleteHouseByIdFromDatabaseUseCase: DeleteHouseByIdFromDatabaseUseCase,
    private val getAllHousesFromDatabaseUseCase: GetAllHousesFromDatabaseUseCase,
) : BaseViewModel<HomeContract.State, HomeContract.Event, HomeContract.Effect>() {
    override fun createInitialState() = HomeContract.State.initial()

    init {
        setEvent(HomeContract.Event.GetListOfHouses)
    }

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnItemClick -> setEffect {
                HomeContract.Effect.NavigateToHouseDetailsScreen(
                    event.id
                )
            }

            is HomeContract.Event.OnAddNewHouseButtonClick -> setEffect {
                HomeContract.Effect.NavigateToAddNewHouseDialog
            }

            is HomeContract.Event.OnDeleteHouseButtonClick -> setEffect {
                HomeContract.Effect.OpenDeleteDialog(
                    event.id
                )
            }

            is HomeContract.Event.DeleteHouse -> deleteHouse(event.id)
            is HomeContract.Event.GetListOfHouses -> getListOfHouses()
        }
    }

    private fun deleteHouse(id: String) = launch {
        deleteHouseByIdFromDatabaseUseCase.invoke(id).collect()
    }

    private fun getListOfHouses() = launch {
        getAllHousesFromDatabaseUseCase.invoke().subscribe { newList ->
            setState {
                copy(listOfHouses = newList.map { it.toHousePresentation() })
            }
        }
    }
}