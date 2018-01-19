package com.linecy.core.data.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author by linecy.
 */
@SuppressLint("ParcelCreator")
@Parcelize data class TabModel(val position: Int, val category: String,
    val description: String?) : Parcelable