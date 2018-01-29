package com.linecy.eyepetizer.data.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author by linecy.
 */
@SuppressLint("ParcelCreator")
@Parcelize data class Author(var id: Int, var icon: String, var name: String?,
    var description: String?,
    var link: String?, var latestReleaseTime: Long, var videoNum: Int) : Parcelable