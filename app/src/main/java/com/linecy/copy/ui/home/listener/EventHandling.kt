package com.linecy.copy.ui.home.listener

import android.content.Intent
import android.view.View
import com.linecy.copy.ui.detail.AuthorDetailActivity
import com.linecy.copy.ui.detail.VideoDetailActivity
import com.linecy.core.data.model.ItemList

/**
 * @author by linecy.
 */


class EventHandling {

  fun onBannerClick(view: View, data: ItemList?) {
    val context = view.context
    context.startActivity(Intent(context, VideoDetailActivity::class.java))

  }

  fun onBannerAuthorClick(view: View,data: ItemList?) {
    val context = view.context
    val intent = Intent(context, AuthorDetailActivity::class.java)
    intent.putExtra(AuthorDetailActivity.EXTRA_DATA, data)
    context.startActivity(intent)
  }

  fun onBannerShareClick(view: View,data: ItemList?) {}
}
