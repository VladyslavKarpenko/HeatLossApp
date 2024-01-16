package com.chi.heat.loss.app.presentation.model

import androidx.annotation.DrawableRes

data class HouseAvatarPresentation(
    @DrawableRes val resourceId: Int,
    val isSelected: Boolean = false
)
