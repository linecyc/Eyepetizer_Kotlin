package com.linecy.copy.mvvm.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import com.linecy.core.EndSubscriber
import com.linecy.core.data.model.HomeModel
import com.linecy.core.repository.HomeRepository
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * @author by linecy
 */
class HomeViewModel @Inject constructor(application: Application,
    private val repository: HomeRepository) : AndroidViewModel(application), LifeEventCallback {


  private var subscriptions = CompositeSubscription()
  val viewStyle = ViewStyle()
  var mObservableHomeModel: MutableLiveData<HomeModel> = MutableLiveData()
  var mHomeModel: ObservableField<HomeModel> = ObservableField()
  private var data: String? = ""


  override fun onStart() {
    loadData(true, "")
  }

  override fun onRefresh() {
    viewStyle.isRefresh.set(true)
    loadData(true, "")
  }

  override fun onLoadMore() {
    if (!viewStyle.isLoading.get()) {
      loadData(false, getData())
    }
  }

  override fun onDestroy() {

  }

  private fun loadData(isFirst: Boolean, next: String) {

    viewStyle.isLoading.set(!viewStyle.isRefresh.get())
    viewStyle.isError.set(false)
    viewStyle.isEmpty.set(false)

    subscriptions.add(
        Observable.just(isFirst).flatMap {
          when (isFirst) {
            true -> repository.getHomeData()

            false -> repository.getHomeMoreData(next, "2")
          }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : EndSubscriber<HomeModel>() {
              override fun onNext(t: HomeModel?) {
                mObservableHomeModel.value = t

              }

              override fun onEnd() {
                viewStyle.isLoading.set(false)
                viewStyle.isRefresh.set(false)
              }

              override fun onError(e: Throwable) {
                super.onError(e)
                viewStyle.isError.set(true)
              }

            }))

  }

  private fun getData(): String {
    val regEx = "[^0-9]"
    val p = Pattern.compile(regEx)
    if (mObservableHomeModel.value != null) {
      val m = p.matcher(mObservableHomeModel.value?.nextPageUrl)
      data = m.replaceAll("").subSequence(1, m.replaceAll("").length - 1).toString()

    }
    return data as String
  }

}