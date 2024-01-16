package com.chi.heat.loss.app.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.chi.heat.loss.app.R

enum class RoomType(@StringRes val type: Int, @DrawableRes val icon: Int) {
    LIVING_ROOM(type = R.string.living_room, icon = R.drawable.ic_living_room),
    FAMILY_ROOM(type = R.string.family_room, icon = R.drawable.ic_falimy_room),
    DINING_ROOM(type = R.string.dining_room, icon = R.drawable.ic_dining_room),
    KITCHEN(type = R.string.kitchen, icon = R.drawable.ic_kitchen),
    GAME_ROOM(type = R.string.game_room, icon = R.drawable.ic_game_room),
    BATHROOM(type = R.string.bathroom, icon = R.drawable.ic_bathroom),
    BEDROOM(type = R.string.bedroom, icon = R.drawable.ic_bedroom),
    LAUNDRY_ROOM(type = R.string.laundry_room, icon = R.drawable.ic_laundry_room),
    SUN_ROOM(type = R.string.sun_room, icon = R.drawable.ic_sun_room),
    STUDY_ROOM(type = R.string.study_room, icon = R.drawable.ic_study_room),
    UTILITY_ROOM(type = R.string.utility_room, icon = R.drawable.ic_utility_room),
}