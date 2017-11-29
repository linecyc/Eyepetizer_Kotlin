package com.linecy.copy.ui.misc

import android.content.Context
import android.util.AttributeSet
import android.widget.ViewAnimator

/**
 * @author by linecy
 */
open class BatterViewAnimator(context: Context?, attrs: AttributeSet?) : ViewAnimator(context,
    attrs) {


  fun setDisplayedChilId(id: Int) {
    if (getDisplayedChildId() == id) {
      return
    }
    for (i in 0 until childCount) {
      if (getChildAt(i).id == id) {
        displayedChild = id
        return
      }
    }

    val name = resources.getResourceEntryName(id)
    throw IllegalArgumentException("No view with ID " + name)
  }


  fun getDisplayedChildId(): Int {
    return getChildAt(displayedChild).id
  }
}