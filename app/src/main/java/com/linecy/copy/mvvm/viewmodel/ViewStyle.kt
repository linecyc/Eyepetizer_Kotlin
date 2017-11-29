package com.linecy.copy.mvvm.viewmodel

import android.databinding.ObservableBoolean

/**
 * @author by linecy
 */
class ViewStyle {
  val isLoading = ObservableBoolean(true)
  val isRefresh = ObservableBoolean(true)
  val isError = ObservableBoolean(false)
  val isEmpty = ObservableBoolean(false)
}