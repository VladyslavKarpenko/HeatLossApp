package com.chi.heat.loss.app.di

import com.chi.heat.loss.app.data.database.HeatLossDatabase
import com.chi.heat.loss.app.data.repository.HeatLossRepositoryImpl
import com.chi.heat.loss.app.domain.repository.HeatLossRepository
import com.chi.heat.loss.app.domain.usecase.*
import com.chi.heat.loss.app.presentation.screen.add_new_house.AddNewHouseViewModel
import com.chi.heat.loss.app.presentation.screen.add_new_room.AddNewRoomViewModel
import com.chi.heat.loss.app.presentation.screen.home.HomeViewModel
import com.chi.heat.loss.app.presentation.screen.house_details.HouseDetailsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val databaseModule = module {
    single { HeatLossDatabase.getDatabase(context = androidContext()) }
    single { get<HeatLossDatabase>().houseDao() }
}

val dataModule = module {
    includes(databaseModule)
    single<HeatLossRepository> { HeatLossRepositoryImpl(heatLossDao = get()) }
}

val domainModule = module {
    factory<AddNewHouseToDatabaseUseCase> { AddNewHouseToDatabaseUseCaseImpl(repository = get()) }
    factory<AddNewRoomToDatabaseUseCase> { AddNewRoomToDatabaseUseCaseImpl(repository = get()) }
    factory<DeleteHouseByIdFromDatabaseUseCase> { DeleteHouseByIdFromDatabaseUseCaseImpl(repository = get()) }
    factory<DeleteRoomByIdFromDatabaseUseCase> { DeleteRoomByIdFromDatabaseUseCaseImpl(repository = get()) }
    factory<GetAllHousesFromDatabaseUseCase> { GetAllHousesFromDatabaseUseCaseImpl(repository = get()) }
    factory<GetHouseByIdFromDatabaseUseCase> { GetHouseByIdFromDatabaseUseCaseImpl(repository = get()) }
    factory<GetAllRoomsByHouseIdFromDatabaseUseCase> {
        GetAllRoomsByHouseIdFromDatabaseUseCaseImpl(
            repository = get()
        )
    }
}

val presentationModule = module {
    viewModel {
        HomeViewModel(
            deleteHouseByIdFromDatabaseUseCase = get(),
            getAllHousesFromDatabaseUseCase = get()
        )
    }
    viewModel {
        AddNewHouseViewModel(
            addNewHouseToDatabaseUseCase = get()
        )
    }
    viewModel {
        HouseDetailsViewModel(
            getHouseByIdFromDatabaseUseCase = get(),
            getAllRoomsByHouseIdFromDatabaseUseCase = get(),
            addNewRoomToDatabaseUseCase = get(),
            deleteRoomByIdFromDatabaseUseCase = get()
        )
    }
    viewModel {
        AddNewRoomViewModel(
            addNewRoomToDatabaseUseCase = get(),
        )
    }
}