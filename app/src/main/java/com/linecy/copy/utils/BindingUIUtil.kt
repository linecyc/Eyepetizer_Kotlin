package com.linecy.copy.utils

import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v4.view.ViewPager
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.linecy.copy.R
import com.linecy.copy.mvvm.ViewStyle
import com.linecy.copy.ui.banner.BannerAdapter
import com.linecy.copy.ui.home.adapter.RecommendAdapter
import com.linecy.copy.ui.misc.ViewContainer
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
    val adapter = listView.adapter as RecommendAdapter?
    adapter?.refreshData(homeModel)
  }


  @JvmStatic
  @BindingAdapter("loadPicture")
  fun setPicture(imageView: ImageView, url: String?) {
    url?.let {
      Glide.with(imageView.context).load(url).into(imageView)
    }
  }

  @JvmStatic
  @BindingAdapter("loadRoundPicture")
  fun setRoundPicture(imageView: ImageView, url: String?) {
    if (!TextUtils.isEmpty(url)) {
      val target = object : BitmapImageViewTarget(imageView) {
        override fun setResource(resource: Bitmap?) {
          super.setResource(resource)
          val roundDrawable = RoundedBitmapDrawableFactory.create(imageView.resources, resource)
          roundDrawable.cornerRadius = 10f
          roundDrawable.setAntiAlias(true)
          imageView.setImageDrawable(roundDrawable)
        }
      }
      Glide.with(imageView.context).load(url).asBitmap().centerCrop().into(target)
    }
  }

  @JvmStatic
  @BindingAdapter("loadCirclePicture")
  fun setCirclePicture(imageView: ImageView, url: String?) {
    if (!TextUtils.isEmpty(url)) {
      val target = object : BitmapImageViewTarget(imageView) {
        override fun setResource(resource: Bitmap?) {
          super.setResource(resource)
          val roundDrawable = RoundedBitmapDrawableFactory.create(imageView.resources, resource)
          val r = if (imageView.width > imageView.height) {
            imageView.height / 2f
          } else {
            imageView.width / 2f
          }
          roundDrawable.cornerRadius = r
          roundDrawable.setAntiAlias(true)
          imageView.setImageDrawable(roundDrawable)
        }
      }
      Glide.with(imageView.context).load(url).asBitmap().centerCrop().into(target)
    }
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
  fun setBanner(viewPager: ViewPager, list: List<HomeModel.ItemList>?) {
    list?.let { viewPager.offscreenPageLimit = list.size }
    val adapter = viewPager.adapter
    if (adapter is BannerAdapter) {
      adapter.refreshData(list)
    }
  }

  /**
   * 两次都是false?
   */
  @JvmStatic
  @BindingAdapter("drawableBottom")
  fun drawableBottom(textView: TextView, drawable: Drawable) {
    if (textView.isSelected) {
      textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable)
    } else {
      textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

    }
  }

}