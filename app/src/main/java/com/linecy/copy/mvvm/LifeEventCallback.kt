package com.linecy.copy.mvvm

/**
 * @author by linecy
 */
interface LifeEventCallback {

  fun onStart()

  fun onRefresh()

  fun onLoadMore()

  fun onDestroy()
}