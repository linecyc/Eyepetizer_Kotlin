package com.linecy.eyepetizer.data.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author by linecy.
 */
@SuppressLint("ParcelCreator")
@Parcelize data class Cover(var feed: String?, var detail: String?,
    var blurred: String?, var sharing: String?, var homepage: String?) : Parcelable
