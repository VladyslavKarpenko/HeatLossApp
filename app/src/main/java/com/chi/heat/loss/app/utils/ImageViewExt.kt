package com.chi.heat.loss.app.utils

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

fun ImageView.loadImage(image: String) {
    Glide.with(this).load(image).into(this)
}

fun ImageView.loadImageWithSize(image: String, width: Int, height: Int) {
    Glide.with(this).load(image).override(
        width,
        height
    ).into(this)
}

fun ImageView.loadImageUrl(url: Uri) {
    Glide.with(this).load(url).into(this)
}

fun ImageView.loadImageFromRes(@DrawableRes resourceId: Int) {
    Glide.with(this).load(resourceId).into(this)
}

fun ImageView.loadCircleImageFromRes(@DrawableRes resourceId: Int) {
    Glide.with(this).load(resourceId).circleCrop().into(this)
}

fun ImageView.loadCircleDrawable(drawable: Drawable) {
    Glide.with(this).load(drawable).circleCrop().into(this)
}