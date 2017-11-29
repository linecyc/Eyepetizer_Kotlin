package com.linecy.copy.mvvm.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.databinding.ObservableBoolean
import com.linecy.core.data.model.HomeModel
import com.linecy.core.repository.HomeRepository
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import timber.log.Timber
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
    //var model: HomeModel
    viewStyle.isRefreshing.set(true)

    subscriptions.add(
        Observable.just(isFirst).flatMap {
          when (isFirst) {
            true -> repository.getHomeData()

            false -> repository.getHomeMoreData(next, "2")
          }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<HomeModel>() {
              override fun onCompleted() {
                viewStyle.isRefreshing.set(false)
                Timber.i("------------------>>>>onCompleted")
              }

              override fun onError(e: Throwable?) {
                viewStyle.isRefreshing.set(false)

                Timber.i("------------------>>>>onError")
              }

              override fun onNext(homeModel: HomeModel) {
               // data = homeModel
                Timber.i("------------------->>onNext")
              }

            }))

  }


}