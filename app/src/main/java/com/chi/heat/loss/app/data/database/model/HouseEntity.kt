package com.chi.heat.loss.app.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "houses")
data class HouseEntity(
    @PrimaryKey val id: String,
    val name: String,
    val picture: Int
)