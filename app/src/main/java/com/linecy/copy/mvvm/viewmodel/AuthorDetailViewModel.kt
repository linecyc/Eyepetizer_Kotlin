package com.linecy.copy.mvvm.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableArrayList
import com.linecy.eyepetizer.data.model.ItemList
import javax.inject.Inject

/**
 * @author by linecy.
 */


class AuthorDetailViewModel @Inject constructor(application: Application) : AndroidViewModel(
    application) {

  val observableList = ObservableArrayList<ItemList>()

  fun loadData(list: List<ItemList>) {
    observableList.addAll(list)
  }
}