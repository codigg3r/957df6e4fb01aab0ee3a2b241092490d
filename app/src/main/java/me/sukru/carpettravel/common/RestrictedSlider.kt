package me.sukru.carpettravel.common

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import com.google.android.material.slider.Slider

class RestrictedSlider(context: Context, attributeSet: AttributeSet? = null) :
    Slider(context, attributeSet) {

    var minValue = valueFrom
    var maxValue = 10F

    private var onChangeListener: ((Float) -> Unit)? = null

    fun setOnChangeListener(listener: (Float) -> Unit) {
        onChangeListener = listener
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        addOnChangeListener { _, _, _ ->
            if (value < minValue) {
                value = minValue
            } else if (value > maxValue) {
                value = maxValue
            }
            onChangeListener?.invoke(value)
        }
    }

}