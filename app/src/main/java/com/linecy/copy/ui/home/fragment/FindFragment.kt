package com.linecy.copy.ui.home.fragment

import android.os.Bundle
import com.linecy.copy.R
import com.linecy.copy.databinding.FragmentFindBinding
import com.linecy.copy.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_find.text

/**
 * @author by linecy
 */
class FindFragment : BaseFragment<FragmentFindBinding>() {


  override fun layoutResId(): Int {
    return R.layout.fragment_find
  }


  override fun onInitView(savedInstanceState: Bundle?) {
    //val text: TextView = fBinding.root.findViewById(R.id.text)
    //text.setOnClickListener({ Toast.makeText(context, "hahah", Toast.LENGTH_SHORT).show() })
    //text.text = "success"

    text.text = "successs"
  }


}