package com.chi.heat.loss.app.data.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "rooms",
    foreignKeys = [
        ForeignKey(
            entity = HouseEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("houseId"),
        )
    ],
    indices = [
        Index(value = arrayOf("houseId"), unique = false)
    ]
)
data class RoomEntity(
    @PrimaryKey val id: String,
    val name: String,
    val heatLoss: Double,
    val houseId: String
)