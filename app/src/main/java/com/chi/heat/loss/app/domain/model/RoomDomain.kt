package com.chi.heat.loss.app.domain.model

data class RoomDomain(
    val id: String,
    val name: String,
    val heatLoss: Double,
    val houseId: String
)