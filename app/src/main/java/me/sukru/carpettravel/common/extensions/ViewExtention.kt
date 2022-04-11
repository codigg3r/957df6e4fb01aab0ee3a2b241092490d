package me.sukru.carpettravel.common.extensions

import android.widget.ImageView


fun ImageView.setImageResourceIf(isTrue: Boolean, trueResource: Int, falseResource: Int) {
    if (isTrue) {
        setImageResource(trueResource)
    } else {
        setImageResource(falseResource)
    }
}