package com.linecy.copy.utils

import android.databinding.BindingAdapter
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.linecy.copy.R
import com.linecy.copy.mvvm.ViewStyle
import com.linecy.copy.ui.home.adapter.HomeAdapter
import com.linecy.copy.ui.misc.ViewContainer
import com.linecy.core.data.model.HomeModel
import com.youth.banner.Banner
import com.youth.banner.BannerConfig

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
    adapter?.refreshData(homeModel)
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

  @JvmStatic
  @BindingAdapter("viewFull")
  fun full(container: ViewContainer, viewStyle: ViewStyle) {
    if (viewStyle.isLoading) {
      container.setDisplayedChildId(viewStyle.contentLayoutId)
    } else {
      when {
        viewStyle.isEmpty -> container.setDisplayedChildId(R.id.empty)
        viewStyle.isError -> container.setDisplayedChildId(R.id.error)
        else -> container.setDisplayedChildId(viewStyle.contentLayoutId)
      }
    }
  }

  @JvmStatic
  @BindingAdapter("loadBanner")
  fun setBanner(banner: Banner, homeModel: HomeModel?) {
    val list = ArrayList<HomeModel.ItemList>()
    if (homeModel?.itemList != null) {
      homeModel.itemList!!
          .filter { it.type.equals("banner2") }
          .forEach { list.add(it) }
    }
    banner.setImageLoader(GlideImageLoader())
    //设置图片集合
    banner.setImages(list)
    //banner设置方法全部调用完毕时最后调用
    banner.setOnBannerListener(
        { position -> println("click banner id:----------->>>" + position) })
    banner.setIndicatorGravity(BannerConfig.CENTER)
    banner.start()
  }

}