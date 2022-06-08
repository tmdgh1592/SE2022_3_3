package com.se.hanger.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

object ScreenSizeProvider {
    fun getWidth(context: Context, windowManager: WindowManager): Int {
        val outMetrics = DisplayMetrics()

        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val display = context.display
            display!!.getRealMetrics(outMetrics)
            display!!.width
        } else {
            @Suppress("DEPRECATION")
            val display = windowManager.defaultDisplay
            @Suppress("DEPRECATION")
            display.getMetrics(outMetrics)
            display.width
        }
    }

    fun getHeight(context: Context, windowManager: WindowManager): Int {
        val outMetrics = DisplayMetrics()

        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val display = context.display
            display!!.getRealMetrics(outMetrics)
            display!!.height
        } else {
            @Suppress("DEPRECATION")
            val display = windowManager.defaultDisplay
            @Suppress("DEPRECATION")
            display.getMetrics(outMetrics)
            display.height
        }
    }


}