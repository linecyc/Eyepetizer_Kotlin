package com.linecy.copy.utils

/**
 * 格式化时间。
 * @author by linecy.
 */
object TimeUtils {

  /**
   * 格式化时间成 00:00:00
   */
  fun formatTime(second: Int): String? {
    val result: String?
    result = if (second >= 60) {
      val min = second / 60
      if (min >= 60) {
        val hour = min / 60
        //超过一天的片会存在？？？
        "${format(hour)}:${format(min % 60)}:${format(second % 60)}"
      } else {
        "${format(min)}:${format(second % 60)}"
      }
    } else {
      "00:${format(second)}"
    }
    return result
  }

  /**
   * 用0填充十位
   */
  private fun format(int: Int): String {
    return if (int > 9) {
      int.toString()
    } else {
      "0$int"
    }
  }
}