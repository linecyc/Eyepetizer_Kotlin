package com.linecy.copy.ui.home.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.linecy.copy.BR
import com.linecy.copy.R
import com.linecy.copy.ui.home.adapter.TabAdapter.ViewHolder
import com.linecy.copy.ui.home.listener.OnTabChangedListener
import com.linecy.core.data.model.TabModel

/**
 * @author by linecy
 */
class TabAdapter(context: Context) : RecyclerView.Adapter<ViewHolder>() {

  private val lists = ArrayList<TabModel>()
  private val inflater: LayoutInflater = LayoutInflater.from(context)
  private var selectPosition: Int = 1
  private var onTabChangedListener: OnTabChangedListener? = null


  override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
    holder?.bindData(lists[position], position)
  }

  override fun getItemCount(): Int {
    return lists.size
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
    val viewBinding = DataBindingUtil.inflate<ViewDataBinding>(inflater, R.layout.item_home_tab,
        parent, false)

    return ViewHolder(viewBinding)
  }


  inner class ViewHolder(private val viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(
      viewDataBinding.root) {
    private val itemTabName: TextView = itemView.findViewById(R.id.itemTabName)
    private var itemPosition = 1

    init {
      itemView.setOnClickListener {
        onTabChangedListener?.onTabChanged(itemPosition)
        setSelectPosition(itemPosition)
      }
    }

    fun bindData(tab: TabModel, position: Int) {
      itemPosition = position
      if (position == selectPosition) {
        itemTabName.typeface = Typeface.DEFAULT_BOLD
        itemTabName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,
            R.drawable.abc_tab_indicator_mtrl_alpha)
      } else {
        itemTabName.typeface = Typeface.DEFAULT

        itemTabName.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
      }
      viewDataBinding.setVariable(BR.homeTab, tab)
      viewDataBinding.executePendingBindings()
    }
  }


  fun refreshData(list: List<TabModel>?) {
    this.lists.clear()
    if (list != null && list.isNotEmpty()) {
      this.lists.addAll(list)
    }
    notifyDataSetChanged()
  }

  fun setSelectPosition(position: Int) {
    this.selectPosition = position
    notifyDataSetChanged()
  }


  fun setOnTabChangedListener(onTabChangedListener: OnTabChangedListener) {
    this.onTabChangedListener = onTabChangedListener
  }
}