package com.linecy.eyepetizer.data.model

import android.os.Parcel
import android.os.Parcelable

/**
 *
 * Type is not directly supported by 'Parcelize'.
 * Annotate the parameter type with '@RawValue' if you want it to be serialized using 'writeValue()'
 *
 * @author by linecy
 */
data class HomeModel(var itemList: List<ItemList>?,
    var count: Int,
    var total: Int,
    var nextPageUrl: String?,
    var adExist: Boolean,
    var date: Long,
    var nextPublishTime: Long,
    var dialog: Any?,
    var topIssue: Any?,
    var refreshCount: Int,
    var lastStartId: Int) : Parcelable {
  constructor(source: Parcel) : this(
      ArrayList<ItemList>().apply { source.readList(this, ItemList::class.java.classLoader) },
      source.readInt(),
      source.readInt(),
      source.readString(),
      1 == source.readInt(),
      source.readLong(),
      source.readLong(),
      source.readValue(Any::class.java.classLoader),
      source.readValue(Any::class.java.classLoader),
      source.readInt(),
      source.readInt()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeList(itemList)
    writeInt(count)
    writeInt(total)
    writeString(nextPageUrl)
    writeInt((if (adExist) 1 else 0))
    writeLong(date)
    writeLong(nextPublishTime)
    writeValue(dialog)
    writeValue(topIssue)
    writeInt(refreshCount)
    writeInt(lastStartId)
  }

  companion object {
    @JvmField
    val CREATOR: Parcelable.Creator<HomeModel> = object : Parcelable.Creator<HomeModel> {
      override fun createFromParcel(source: Parcel): HomeModel = HomeModel(source)
      override fun newArray(size: Int): Array<HomeModel?> = arrayOfNulls(size)
    }
  }
}