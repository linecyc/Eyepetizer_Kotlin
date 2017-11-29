package com.linecy.copy.ui.splash

import android.arch.lifecycle.ViewModel
import android.os.Bundle
import com.linecy.copy.R
import com.linecy.copy.databinding.ActivitySplashBinding
import com.linecy.copy.ui.BaseActivity

/**
 * @author by linecy
 */
class SplashActivity : BaseActivity<ActivitySplashBinding, ViewModel>() {
  override fun layoutResId(): Int {
    return R.layout.activity_splash
  }

  override fun onInitView(savedInstanceState: Bundle?) {
  }

}