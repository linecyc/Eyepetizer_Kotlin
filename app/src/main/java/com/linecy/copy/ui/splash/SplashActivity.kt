package com.linecy.copy.ui.splash

import android.arch.lifecycle.ViewModel
import android.databinding.ViewDataBinding
import android.os.Bundle
import com.linecy.copy.R
import com.linecy.copy.ui.BaseActivity

/**
 * @author by linecy
 */
class SplashActivity : BaseActivity<ViewDataBinding, ViewModel>() {
  override fun layoutResId(): Int {
    return R.layout.activity_splash
  }

  override fun onInitView(savedInstanceState: Bundle?) {
  }

}