package com.linecy.copy.utils

/**
 * @author by linecy
 */

import android.app.Activity
import android.view.View
import android.view.ViewGroup

interface AppContainer {

  fun bind(var1: Activity): ViewGroup

  companion object {
    val DEFAULT: AppContainer = object : AppContainer {
      override fun bind(var1: Activity): ViewGroup {
        return var1.findViewById<View>(16908290) as ViewGroup
      }
    }
  }
}
