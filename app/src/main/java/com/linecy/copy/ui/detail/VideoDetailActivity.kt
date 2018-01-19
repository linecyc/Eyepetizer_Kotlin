package com.linecy.copy.ui.detail

import android.arch.lifecycle.ViewModel
import android.databinding.ViewDataBinding
import android.os.Bundle
import com.linecy.copy.R
import com.linecy.copy.ui.BaseActivity

/**
 * @author by linecy.
 */
class VideoDetailActivity : BaseActivity<ViewDataBinding, ViewModel>() {
  override fun layoutResId(): Int {
    return R.layout.activity_video_detail
  }

  override fun onInitView(savedInstanceState: Bundle?) {

    setToolbarTitle("视频详情")
    setDisplayHomeAsUp(true)
  }

}