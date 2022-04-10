package me.sukru.carpettravel.common.extensions


fun Double.nDecimal(n: Int): Double {
    return "%.${n}f".format(this).toDouble()
}