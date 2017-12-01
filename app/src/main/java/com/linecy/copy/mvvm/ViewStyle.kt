package com.linecy.copy.mvvm

import android.support.annotation.IdRes

/**
 * 统一样式
 *
 * @author by linecy
 */
class ViewStyle(builder: Builder) {


  companion object {
    fun builder(): Builder {
      return Builder()
    }
  }

  var isLoading: Boolean = builder.isLoading

  var isRefresh: Boolean = builder.isRefresh

  var isError: Boolean = builder.isError

  var isEmpty: Boolean = builder.isEmpty
  //val isEmpty = ObservableBoolean(false)

  var contentLayoutId = builder.contentLayoutId


  class Builder {
    var contentLayoutId: Int = 0
    var isLoading: Boolean = false
    var isRefresh: Boolean = false
    var isError: Boolean = false
    var isEmpty: Boolean = false

    fun contentId(@IdRes resId: Int): Builder {
      contentLayoutId = resId
      return this
    }

    fun isLoading(boolean: Boolean): Builder {
      isLoading = boolean
      return this
    }

    fun isRefresh(boolean: Boolean): Builder {
      isRefresh = boolean
      return this
    }

    fun isError(boolean: Boolean): Builder {
      isError = boolean
      return this
    }

    fun isEmpty(boolean: Boolean): Builder {
      isEmpty = boolean
      return this
    }

    fun build(): ViewStyle {
      return ViewStyle(this)
    }
  }

  override fun toString(): String {
    return "------>>isLoading:" + isLoading + "<<--->>isEmpty:" + isEmpty + "<<--->>isError:" + isError + "<<--->>isRefresh:" + isRefresh + "<<---layoutid:" + contentLayoutId
  }
}