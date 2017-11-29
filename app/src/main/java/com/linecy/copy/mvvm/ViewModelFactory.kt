package com.linecy.copy.mvvm

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * kotlin 的时候，当KClass在注释中使用时，它实际上被编译成Java的Class。
 * 但实际问题是java.util.Map<kotlin.reflect.KClass<? extends android.arch.lifecycle.ViewModel>,?
 * extends javax.inject.Provider<android.arch.lifecycle.ViewModel>>Kotlin编译器正在生成的通配符。
 * 使用@JvmSuppressWildcards将会阻止编译器生成通配符。
 *
 * @author by linecy
 */
@Singleton
class ViewModelFactory @Inject
constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    var creator: Provider<out ViewModel>? = creators[modelClass]
    if (creator == null) {
      for ((key, value) in creators) {
        if (modelClass.isAssignableFrom(key)) {
          creator = value
          break
        }
      }
    }
    if (creator == null) {
      throw IllegalArgumentException("unknown model class " + modelClass)
    }
    try {
      return creator.get() as T
    } catch (e: Exception) {
      throw RuntimeException(e)
    }

  }
}


