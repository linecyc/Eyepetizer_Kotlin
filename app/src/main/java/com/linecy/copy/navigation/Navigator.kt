package com.linecy.copy.navigation

import android.content.Context
import android.content.Intent
import com.linecy.copy.ui.detail.AuthorDetailActivity
import com.linecy.copy.ui.detail.VideoDetailActivity
import com.linecy.eyepetizer.data.model.ItemList
import javax.inject.Singleton

/**
 * @author by linecy.
 */
@Singleton
class Navigator {
  companion object {
    const val EXTRA_DATA = "extra_data"
    const val EXTRA_BUNDLE = "extra_bundle"
  }

  fun navitateToVideoDetail(context: Context, data: ItemList?) {
    val intent = Intent(context, VideoDetailActivity::class.java)
    intent.putExtra(EXTRA_DATA, data)
    context.startActivity(intent)
  }

  fun navitateToAuthorDetail(context: Context, data: ItemList?) {
    val intent = Intent(context, AuthorDetailActivity::class.java)
    intent.putExtra(EXTRA_DATA, data)
    context.startActivity(intent)
  }


}