package com.linecy.eyepetizer.data.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author by linecy.
 */
@SuppressLint("ParcelCreator")
@Parcelize data class Provider(var name: String?, var alias: String?,
    var icon: String?) : Parcelable
