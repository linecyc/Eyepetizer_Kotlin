package com.linecy.copy.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.linecy.core.data.model.HomeModel.ItemList
import com.youth.banner.loader.ImageLoader

/**
 * @author by linecy
 */

class GlideImageLoader : ImageLoader() {

  override fun displayImage(context: Context, path: Any, imageView: ImageView) {
    val list: ItemList = path as ItemList
    Glide.with(context)
        .load(list.data?.image)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()
        .crossFade()
        .into(imageView)
  }

  override fun createImageView(context: Context?): ImageView {
    return super.createImageView(context)
  }
}
