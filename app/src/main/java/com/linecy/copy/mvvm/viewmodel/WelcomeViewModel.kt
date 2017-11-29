package com.linecy.copy.mvvm.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Intent
import com.linecy.copy.CopyApplication
import com.linecy.copy.ui.home.MainActivity
import javax.inject.Inject

/**
 * @author by linecy
 */
class WelcomeViewModel @Inject constructor(
    application: Application) : AndroidViewModel(
    application) {

  fun jump() {
    val a = getApplication<CopyApplication>()
    a.startActivity(Intent(a, MainActivity::class.java))
  }

  fun setPage() {

  }

  override fun onCleared() {

    super.onCleared()

  }
}