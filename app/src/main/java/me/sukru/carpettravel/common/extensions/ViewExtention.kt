package me.sukru.carpettravel.common.extensions

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import me.sukru.carpettravel.R


fun ImageView.setImageResourceIf(isTrue: Boolean, trueResource: Int, falseResource: Int) {
    if (isTrue) {
        setImageResource(trueResource)
    } else {
        setImageResource(falseResource)
    }
}

fun View.setBackgroundTintListIf(isTrue: Boolean, trueResource: Int, falseResource: Int) {
    val tintList = if (isTrue) {
        ContextCompat.getColorStateList(context, trueResource)
    } else {
        ContextCompat.getColorStateList(context, falseResource)
    }
    backgroundTintList = tintList
}