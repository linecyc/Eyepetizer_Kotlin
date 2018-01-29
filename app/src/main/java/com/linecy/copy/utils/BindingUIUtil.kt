package com.linecy.copy.utils

import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v4.view.ViewPager
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.TextAppearanceSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.ViewTarget
import com.linecy.copy.R
import com.linecy.copy.mvvm.ViewStyle
import com.linecy.copy.ui.banner.BannerAdapter
import com.linecy.copy.ui.detail.adapter.AuthorDetailAdapter
import com.linecy.copy.ui.home.adapter.RecommendAdapter
import com.linecy.copy.ui.misc.ViewContainer
import com.linecy.eyepetizer.data.model.HomeModel
import com.linecy.eyepetizer.data.model.ItemList


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
  @BindingAdapter("setBackground")
  fun setBackground(view: View, url: String?) {
    val target = object : ViewTarget<View, GlideDrawable>(view) {
      override fun onResourceReady(resource: GlideDrawable?,
          glideAnimation: GlideAnimation<in GlideDrawable>?) {
        this.view.background = resource?.current
      }

    }
    Glide.with(view.context).load(url).into(target)
  }

  @JvmStatic
  @BindingAdapter("setRoundBackground")
  fun setRoundBackground(view: View, url: String?) {
    if (!TextUtils.isEmpty(url)) {

      val target = object : SimpleTarget<Bitmap>() {
        override fun onResourceReady(resource: Bitmap?,
            glideAnimation: GlideAnimation<in Bitmap>?) {
          val roundDrawable = RoundedBitmapDrawableFactory.create(view.resources, resource)
          val r = if (view.width > view.height) {
            view.height / 2f
          } else {
            view.width / 2f
          }
          roundDrawable.cornerRadius = r
          roundDrawable.setAntiAlias(true)
          view.background = roundDrawable
        }

      }
      Glide.with(view.context).load(url).asBitmap().centerCrop().into(target)
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
  fun setBanner(viewPager: ViewPager, list: List<ItemList>?) {
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


  @JvmStatic
  @BindingAdapter("authorDetail")
  fun setAuthorDetail(textView: TextView, charSequence: CharSequence) {
    if (!TextUtils.isEmpty(charSequence)) {
      val array = charSequence.split("\n")
      if (null != array && array.size == 2) {
        val start = array[0]
        if (null != start && !TextUtils.isEmpty(start)) {
          val span = SpannableString(charSequence)
          span.setSpan(TextAppearanceSpan(textView.context, R.style.Text_Bold), 0, start.length,
              Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
          span.setSpan(TextAppearanceSpan(textView.context, R.style.Text_Normal), start.length,
              charSequence.length,
              Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
          //span.toString() 调用toString会导致span无效
          textView.text = span
        }
      } else {
        textView.text = charSequence
      }
    }
  }

  @JvmStatic
  @BindingAdapter("loadAdapterData")
  fun setAdapterData(listView: RecyclerView, list: List<ItemList>?) {
    val adapter = listView.adapter as AuthorDetailAdapter?
    adapter?.refreshData(list)
  }
}