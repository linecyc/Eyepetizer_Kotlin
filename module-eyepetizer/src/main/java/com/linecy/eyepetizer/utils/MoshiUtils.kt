package com.linecy.eyepetizer.utils

import android.text.TextUtils
import com.squareup.moshi.Moshi

object MoshiUtils {

  fun <T> objectToStr(obj: Any, objType: Class<T>): String {
    val moshi = Moshi.Builder().build()
    val jsonAdapter = moshi.adapter(objType)
    var json = ""
    try {
      json = jsonAdapter.toJson(obj as T)
    } catch (e: Exception) {
      e.printStackTrace()
    }

    return json
  }

  fun <T> strToObject(str: String, objType: Class<T>): T? {
    var result: T? = null
    if (!TextUtils.isEmpty(str)) {
      val moshi = Moshi.Builder().build()
      val jsonAdapter = moshi.adapter(objType)
      try {
        result = jsonAdapter.fromJson(str)
      } catch (e: Exception) {
        e.printStackTrace()
      }

    }
    return result
  }
}
