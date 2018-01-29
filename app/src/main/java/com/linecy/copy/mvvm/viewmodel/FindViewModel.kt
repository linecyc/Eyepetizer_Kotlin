package com.linecy.copy.mvvm.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Context
import android.databinding.ObservableBoolean
import com.linecy.eyepetizer.repository.HomeRepository
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * @author by linecy
 */
class FindViewModel @Inject constructor(application: Application,
    private val repository: HomeRepository) : AndroidViewModel(application) {
  private var subscriptions = CompositeSubscription()
  val viewStyle = ViewStyle()

  var text: String? = "test"

  val loading = ObservableBoolean(false)

  inner class ViewStyle {
    val isRefreshing = ObservableBoolean(true)
    val progressRefreshing = ObservableBoolean(true)
  }

  fun loadData(context: Context, isFirst: Boolean, next: String) {


  }

}