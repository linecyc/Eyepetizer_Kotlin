package com.linecy.copy.ui.home.popupwindow

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupWindow
import android.widget.TextView
import com.linecy.copy.R
import com.linecy.copy.ui.misc.ItemTouchCallback
import com.linecy.core.data.model.TabModel

/**
 * @author by linecy.
 */
class TabPopWindow(context: Context) : PopupWindow() {
  private var tabRecyclerView: RecyclerView
  private val adapter by lazy { TabAdapter(context) }

  init {
    contentView = LayoutInflater.from(context).inflate(R.layout.pop_tab, null)
    tabRecyclerView = contentView.findViewById(R.id.tabRecyclerView)
    tabRecyclerView.adapter = adapter
    tabRecyclerView.layoutManager = LinearLayoutManager(context)

    val helper = ItemTouchHelper(ItemTouchCallback())
    helper.attachToRecyclerView(tabRecyclerView)
    val title = contentView.findViewById<TextView>(R.id.containerTitle)
    val containerUp = contentView.findViewById<ImageButton>(R.id.containerUp)

    title.setText(R.string.tab_title)
    containerUp.setImageResource(R.drawable.ic_back)
    containerUp.visibility = View.VISIBLE
    containerUp.setOnClickListener { dismiss() }
    width = ViewGroup.LayoutParams.MATCH_PARENT
    height = ViewGroup.LayoutParams.MATCH_PARENT
    setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(context, R.color.white)))
    isFocusable = true
    isOutsideTouchable = true
    animationStyle = R.style.BottomPopupWindow
  }


  fun refreshData(list: List<TabModel>?) {
    adapter.refreshData(list)
  }

}