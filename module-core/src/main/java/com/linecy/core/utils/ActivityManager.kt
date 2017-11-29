package com.linecy.core.utils

import android.app.Activity
import java.lang.ref.WeakReference
import java.util.ArrayList

/**
 * @author by linecy
 */
class ActivityManager {
  private val activityWeakRef: MutableList<WeakReference<Activity>>

  init {
    activityWeakRef = ArrayList()
  }

  companion object {
    fun <T> checkNotNull(reference: T?, message: String = ("activity==null")): T {
      if (reference == null) {
        throw NullPointerException(message)
      }
      return reference
    }
  }

  fun add(activity: Activity) {
    checkNotNull(activity)
    val weakRef = WeakReference(activity)
    activityWeakRef.add(weakRef)
  }

  fun size(): Int {
    return activityWeakRef.size
  }


  fun getTopActivity(): Activity? {
    val size = size()
    if (size > 0) {
      return activityWeakRef[size - 1].get()
    }
    return null
  }


  fun contains(activity: Activity?): Boolean {
    if (null != activity) {
      val size = size()
      (0 until size)
          .asSequence()
          .map { activityWeakRef[it] }
          .map { it.get() }
          .forEach { return it == activity }
    }
    return false
  }

  fun remove(activity: Activity): Boolean {
    checkNotNull(activity)
    val size = size()
    for (i in 0 until size) {
      val weakRef = activityWeakRef[i]
      val a = weakRef.get()
      if (a == activity) {
        activityWeakRef.remove(weakRef)
        finish(a)
        return true
      }
    }
    return false
  }


  fun clear() {
    val size = size()
    (0 until size)
        .asSequence()
        .map { activityWeakRef[it] }
        .forEach { finish(it) }

    activityWeakRef.clear()
  }


  private fun finish(weakReference: WeakReference<Activity>) {
    val a = weakReference.get()
    finish(a)
  }


  private fun finish(activity: Activity?) {
    if (activity != null && !activity.isFinishing) {
      activity.finish()
    }
  }
}