package com.linecy.copy.ui.splash.fragment

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.linecy.copy.R
import com.linecy.copy.databinding.FragmentWelcomeBinding
import com.linecy.copy.mvvm.viewmodel.WelcomeViewModel
import com.linecy.copy.ui.BaseFragment
import com.linecy.copy.ui.home.MainActivity

/**
 * @author by linecy
 */
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {

  companion object {
    fun newInstance(page: CharSequence?): WelcomeFragment {
      val fragment = WelcomeFragment()
      val bundle = Bundle()
      bundle.putCharSequence("page", page)
      fragment.arguments = bundle

      return fragment
    }
  }


  override fun layoutResId(): Int {
    return R.layout.fragment_welcome
  }

  override fun onInitView(savedInstanceState: Bundle?) {
    val welcomeViewModule = ViewModelProviders.of(this, mViewModelFactory).get(
        WelcomeViewModel::class.java)
    fBinding.tvPage.text = arguments.getCharSequence("page")
  }

  fun onSkip(v: View) {
    activity.startActivity(Intent(activity, MainActivity::class.java))
    activity.finish()
  }

}