package com.linecy.copy.ui

import android.databinding.ViewDataBinding
import android.os.Bundle
import com.linecy.copy.R
import com.linecy.eyepetizer.data.model.TabModel
import kotlinx.android.synthetic.main.fragment_empty.textView

/**
 * @author by linecy.
 */
class EmptyFragment : BaseFragment<ViewDataBinding>() {


  companion object {
    private val EXTRA_DATA = "extra_data"
    fun newInstance(tabModel: TabModel): EmptyFragment {
      val fragment = EmptyFragment()
      val bundle = Bundle()
      bundle.putParcelable(EXTRA_DATA, tabModel)
      fragment.arguments = bundle
      return fragment
    }
  }

  override fun layoutResId(): Int {
    return R.layout.fragment_empty
  }

  override fun onInitView(savedInstanceState: Bundle?) {
    val tabModel: TabModel? = arguments?.getParcelable(EXTRA_DATA)
    if (null != tabModel) {
      textView.text = tabModel.description
    }
  }

}