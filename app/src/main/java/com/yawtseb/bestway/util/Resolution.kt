package com.yawtseb.bestway.util

import android.content.Context
import android.util.TypedValue

class Resolution {

    companion object{
        fun Int.toPx(context: Context): Float {
            return this * context.resources.displayMetrics.density
        }

        fun Float.toDp(context: Context): Float {
            // 해상도 마다 다른 density 를 반환. xxxhdpi는 density = 4
            var density = context.resources.displayMetrics.density
            if (density.toDouble() == 1.0) // mpdi  (160dpi) -- xxxhdpi (density = 4)기준으로 density 값을 재설정 한다
                density *= 4.0f else if (density.toDouble() == 1.5) // hdpi  (240dpi)
                density *= (8 / 3).toFloat() else if (density.toDouble() == 2.0) // xhdpi (320dpi)
                density *= 2.0f
            return this / density // dp 값 반환
        }

        fun Context.getDimension(value: Float): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, value, this.resources.displayMetrics).toInt()
        }
    }
}