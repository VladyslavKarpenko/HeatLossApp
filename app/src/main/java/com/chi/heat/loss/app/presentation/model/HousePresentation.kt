package com.chi.heat.loss.app.presentation.model

import androidx.annotation.DrawableRes

data class HousePresentation(
    val id: String = System.currentTimeMillis().toString(),
    val name: String,
    @DrawableRes val picture: Int
)
