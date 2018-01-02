package com.linecy.copy.ui.banner

import android.content.Context
import android.view.View

/**
 * @author by linecy.
 */
interface BannerCreator {

  fun onCreateView(context: Context): View

  fun onBindData(context: Context, position: Int, data: Any?)
}