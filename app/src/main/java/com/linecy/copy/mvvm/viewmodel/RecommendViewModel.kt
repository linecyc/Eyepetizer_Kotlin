package com.linecy.copy.mvvm.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableField
import com.linecy.copy.R
import com.linecy.copy.mvvm.LifeEventCallback
import com.linecy.copy.mvvm.OnLoadingStateChangedListener
import com.linecy.copy.mvvm.ViewStyle
import com.linecy.core.EndSubscriber
import com.linecy.core.data.model.HomeModel
import com.linecy.core.repository.HomeRepository
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * @author by linecy
 */
class RecommendViewModel @Inject constructor(application: Application,
    private val repository: HomeRepository) : AndroidViewModel(application), LifeEventCallback {


  private var subscriptions = CompositeSubscription()

  //好像对同一个对象无效？
  val observableViewStyle: ObservableField<ViewStyle> = ObservableField(
      ViewStyle.builder().contentId(R.id.content).build())
  val observableHomeModel: ObservableField<HomeModel> = ObservableField()
  private var date: String? = null
  private var listener: OnLoadingStateChangedListener? = null

  fun addOnLoadingStateChangedListener(l: OnLoadingStateChangedListener) {
    this.listener = l
  }

  override fun onStart() {
    observableViewStyle.set(
        ViewStyle.builder().contentId(R.id.content).isLoading(true).build())
    loadData(true, date)
  }

  override fun onRefresh() {
    observableViewStyle.set(
        ViewStyle.builder().contentId(R.id.content).isRefresh(true).isLoading(true).build())
    loadData(true, date)
  }

  override fun onLoadMore() {
    if (!observableViewStyle.get().isLoading) {
      observableViewStyle.set(
          ViewStyle.builder().contentId(R.id.content).isLoading(true).build())
      loadData(false, date)
    }
  }

  override fun onDestroy() {
    onCleared()
  }

  private fun loadData(isFirst: Boolean, next: String?) {
    listener?.onShowLoading()
    subscriptions.add(
        Observable.just(isFirst).flatMap {
          when (isFirst) {
            true -> repository.getHomeMoreData(null, null)

            false -> repository.getHomeMoreData(next, "2")
          }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : EndSubscriber<HomeModel>() {
              override fun onNext(t: HomeModel?) {

                if (isFirst && (t == null || t.count == 0)) {
                  observableViewStyle.set(
                      ViewStyle.builder().isEmpty(true).contentId(R.id.content).build())
                } else {
                  observableViewStyle.set(
                      ViewStyle.builder().contentId(R.id.content).build())
                }
                date = t?.date!!.toString()
                observableHomeModel.set(t)

              }

              override fun onEnd() {
                listener?.onHideLoading()
              }

              override fun onError(e: Throwable) {
                super.onError(e)
                observableViewStyle.set(
                    ViewStyle.builder().contentId(R.id.content).isError(true).build())
              }

            }))

  }

}