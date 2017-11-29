package com.linecy.copy.utils

import android.databinding.BindingAdapter
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.linecy.copy.ui.home.adapter.HomeAdapter
import com.linecy.core.data.model.HomeModel

/**
 * 自定义dataBinding属性需要标记为静态方法，
 * 在kotlin中如果整个类都是静态的方法 将这个对象用object标记即可定义;
 * 或者将需要标记为静态的方法放入到companion object {}里面即可;
 *
 * 坑的地方来了kotlin+dataBinding像上面那样写没卵用，需要用@JvmStatic标记为Java的静态方法。
 *
 * @author by linecy
 */
object BindingUIUtil {


  @JvmStatic
  @BindingAdapter("homeAdapter")
  fun setItems(listView: RecyclerView, homeModel: HomeModel?) {
    val adapter = listView.adapter as HomeAdapter?
    adapter?.addData(homeModel)
  }


  @JvmStatic
  @BindingAdapter("loadPicture")
  fun setPicture(imageView: ImageView, url: String?) {
    url?.let { ImageLoadUtils.display(imageView.context, imageView, it) }
  }


  @JvmStatic
  @BindingAdapter("showHide")
  fun showHide(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
  }

  @JvmStatic
  @BindingAdapter("refresh")
  fun refresh(view: SwipeRefreshLayout, refresh: Boolean) {
    view.isRefreshing = refresh
  }
}