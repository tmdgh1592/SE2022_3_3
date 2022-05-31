package com.se.hanger.view.calendar

import androidx.annotation.IdRes
import com.se.hanger.R

enum class DateColor(
    @IdRes private val colorResId: Int
) {
    RED(android.R.color.holo_red_light),
    BLUE(android.R.color.holo_red_light),
    PRIMARY(R.color.colorPrimary);

    fun getColorResId(): Int = this.colorResId
}