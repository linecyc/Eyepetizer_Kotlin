package com.linecy.copy.ui

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.text.TextUtils
import com.linecy.copy.R
import kotlinx.android.synthetic.main.fragment_empty.textView

/**
 * @author by linecy.
 */
class EmptyFragment : BaseFragment<ViewDataBinding>() {


  companion object {
    private val EXTRA_DATA = "extra_data"
    fun newInstance(string: String): EmptyFragment {
      val fragment = EmptyFragment()
      val bundle = Bundle()
      bundle.putString(EXTRA_DATA, string)
      fragment.arguments = bundle
      return fragment
    }
  }

  override fun layoutResId(): Int {
    return R.layout.fragment_empty
  }

  override fun onInitView(savedInstanceState: Bundle?) {
    val string = arguments.getString(EXTRA_DATA)
    if (!TextUtils.isEmpty(string)) {
      textView.text = string
    }
  }

}