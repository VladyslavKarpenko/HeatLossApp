package com.chi.heat.loss.app.domain.model

import androidx.annotation.DrawableRes

data class HouseDomain(
    val id: String,
    val name: String,
    @DrawableRes val picture: Int
)
