package com.linecy.copy.ui.home.listener

/**
 * @author by linecy.
 */
interface OnItemClickListener<in T> {


  fun onItemClick(position: Int, data: T?)

  fun onAuthorItemClick(position: Int, data: T?)
}