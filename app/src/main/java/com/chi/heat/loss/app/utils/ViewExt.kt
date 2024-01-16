package com.chi.heat.loss.app.utils

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible

fun View.hide() {
    this.isVisible = false
}

fun View.show() {
    this.isVisible = true
}

fun ConstraintLayout.isAllChildEnabled(isEnabled: Boolean) {
    repeat(childCount) {
        val child = getChildAt(it)
        child.isEnabled = isEnabled
    }
}