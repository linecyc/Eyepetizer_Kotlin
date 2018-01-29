package com.linecy.eyepetizer.data.model

import android.os.Parcel
import android.os.Parcelable

/**
 * @author by linecy.
 */
data class Data(var dataType: String?, var id: Int, var title: String?, var slogan: String?,
    var description: String?, var image: String?, var provider: Provider?,
    var category: String?,
    var actionUrl: String?,
    var adTrack: Any?, var isShade: Boolean,
    var label: Any?, var labelList: Any?, var header: Any?,
    var duration: Long?, var playUrl: String, var cover: Cover?,
    var author: Author?,
    var releaseTime: Long?, var consumption: Consumption?, var campaign: Any?,
    var waterMarks: Any?,var text:String?) : Parcelable {
  constructor(source: Parcel) : this(
      source.readString(),
      source.readInt(),
      source.readString(),
      source.readString(),
      source.readString(),
      source.readString(),
      source.readParcelable<Provider>(Provider::class.java.classLoader),
      source.readString(),
      source.readString(),
      source.readValue(Any::class.java.classLoader),
      1 == source.readInt(),
      source.readValue(Any::class.java.classLoader),
      source.readValue(Any::class.java.classLoader),
      source.readValue(Any::class.java.classLoader),
      source.readValue(Long::class.java.classLoader) as Long?,
      source.readString(),
      source.readParcelable<Cover>(Cover::class.java.classLoader),
      source.readParcelable<Author>(Author::class.java.classLoader),
      source.readValue(Long::class.java.classLoader) as Long?,
      source.readParcelable<Consumption>(Consumption::class.java.classLoader),
      source.readValue(Any::class.java.classLoader),
      source.readValue(Any::class.java.classLoader),
      source.readString()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeString(dataType)
    writeInt(id)
    writeString(title)
    writeString(slogan)
    writeString(description)
    writeString(image)
    writeParcelable(provider, 0)
    writeString(category)
    writeString(actionUrl)
    writeValue(adTrack)
    writeInt((if (isShade) 1 else 0))
    writeValue(label)
    writeValue(labelList)
    writeValue(header)
    writeValue(duration)
    writeString(playUrl)
    writeParcelable(cover, 0)
    writeParcelable(author, 0)
    writeValue(releaseTime)
    writeParcelable(consumption, 0)
    writeValue(campaign)
    writeValue(waterMarks)
    writeString(text)
  }

  companion object {
    @JvmField
    val CREATOR: Parcelable.Creator<Data> = object : Parcelable.Creator<Data> {
      override fun createFromParcel(source: Parcel): Data = Data(source)
      override fun newArray(size: Int): Array<Data?> = arrayOfNulls(size)
    }
  }
}