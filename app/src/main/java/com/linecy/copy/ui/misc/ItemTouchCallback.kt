package com.linecy.copy.ui.misc

import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.linecy.copy.R

/**
 * @author by linecy.
 */

class ItemTouchCallback : ItemTouchHelper.Callback() {

  /**
   * dragFlags 是拖拽标志，
   * swipeFlags 是滑动标志。
   */
  override fun getMovementFlags(recyclerView: RecyclerView,
      viewHolder: RecyclerView.ViewHolder): Int {
    if (recyclerView.layoutManager is GridLayoutManager) {
      val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
      val swipeFlags = 0

      return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
    } else {
      val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
      val swipeFlags = 0
      //int swipeFlags = ItemTouchHelper.START /*| ItemTouchHelper.END*/;

      return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
    }
  }

  override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
      target: RecyclerView.ViewHolder): Boolean {

    val fromPosition = viewHolder.adapterPosition

    val toPosition = target.adapterPosition
    recyclerView.adapter.notifyItemMoved(fromPosition, toPosition)
    return true
  }

  override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
    if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
      viewHolder!!.itemView.setBackgroundColor(
          ContextCompat.getColor(viewHolder.itemView.context, R.color.white_button_pressed))
    }

    super.onSelectedChanged(viewHolder, actionState)
  }

  override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder) {
    super.clearView(recyclerView, viewHolder)
    viewHolder.itemView.setBackgroundColor(
        ContextCompat.getColor(viewHolder.itemView.context, R.color.white))
  }

  override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
  }
}
