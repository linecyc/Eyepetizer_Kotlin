package com.linecy.copy.ui.splash.fragment

import android.content.Context
import android.content.Intent
import android.databinding.ViewDataBinding
import android.os.Bundle
import com.linecy.copy.R
import com.linecy.copy.ui.BaseFragment
import com.linecy.copy.ui.home.MainActivity
import kotlinx.android.synthetic.main.fragment_welcome.btnSkip
import kotlinx.android.synthetic.main.fragment_welcome.tvPage

/**
 * @author by linecy
 */
class WelcomeFragment : BaseFragment<ViewDataBinding>() {
  private var message: String? = null

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
    message = arguments.getString("page")
    tvPage.text = message
    tvPage.start()

    btnSkip.setOnClickListener {
      val preferences = activity.getSharedPreferences("my_copy", Context.MODE_PRIVATE)
      preferences.edit().putBoolean("lunch", true).apply()
      activity.startActivity(Intent(activity, MainActivity::class.java))
      activity.finish()
    }
  }

  override fun setUserVisibleHint(isVisibleToUser: Boolean) {
    super.setUserVisibleHint(isVisibleToUser)
    if (isVisibleToUser) {
      if (null != tvPage) {
        tvPage.start()
      }
    } else {
      if (null != tvPage) {
        tvPage.stop()
      }
    }
  }

}