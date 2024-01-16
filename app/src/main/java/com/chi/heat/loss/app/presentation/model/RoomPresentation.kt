package com.chi.heat.loss.app.presentation.model

data class RoomPresentation(
    val id: String,
    val name: String,
    val heatLoss: Double,
    val houseId: String
)