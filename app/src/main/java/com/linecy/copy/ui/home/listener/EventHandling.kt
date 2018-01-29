package com.linecy.copy.ui.home.listener

import android.content.Intent
import android.view.View
import com.linecy.copy.navigation.Navigator
import com.linecy.copy.ui.detail.AuthorDetailActivity
import com.linecy.copy.ui.detail.VideoDetailActivity
import com.linecy.eyepetizer.data.model.ItemList

/**
 * @author by linecy.
 */


class EventHandling {

  fun onBannerClick(view: View, data: ItemList?) {
    val context = view.context

    val intent = Intent(context, VideoDetailActivity::class.java)
    intent.putExtra(Navigator.EXTRA_DATA, data)
    context.startActivity(intent)
  }

  fun onBannerAuthorClick(view: View, data: ItemList?) {
    val context = view.context
    val intent = Intent(context, AuthorDetailActivity::class.java)
    intent.putExtra(Navigator.EXTRA_DATA, data)
    context.startActivity(intent)
  }

  fun onBannerShareClick(view: View, data: ItemList?) {}
}
