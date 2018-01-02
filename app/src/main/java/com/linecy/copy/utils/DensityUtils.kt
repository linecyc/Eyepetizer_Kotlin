package com.linecy.copy.utils

import android.content.Context
import android.util.TypedValue


object DensityUtils {


  fun dp2px(context: Context, dpValue: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue,
        context.resources.displayMetrics).toInt()
  }


  fun px2dp(context: Context, pxValue: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pxValue,
        context.resources.displayMetrics).toInt()
  }
}// No instances.
