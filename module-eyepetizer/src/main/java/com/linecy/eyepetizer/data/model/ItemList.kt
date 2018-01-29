package com.linecy.eyepetizer.data.model

import android.os.Parcel
import android.os.Parcelable

/**
 *
 * @author by linecy.
 */
data class ItemList(var type: String?, var data: Data?, var tag: Any?,
    var id: Int) : Parcelable {
  constructor(source: Parcel) : this(
      source.readString(),
      source.readParcelable<Data>(Data::class.java.classLoader),
      source.readValue(Any::class.java.classLoader),
      source.readInt()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeString(type)
    writeParcelable(data, 0)
    writeValue(tag)
    writeInt(id)
  }

  companion object {
    @JvmField
    val CREATOR: Parcelable.Creator<ItemList> = object : Parcelable.Creator<ItemList> {
      override fun createFromParcel(source: Parcel): ItemList = ItemList(source)
      override fun newArray(size: Int): Array<ItemList?> = arrayOfNulls(size)
    }
  }
}
