package com.stayfit.stayfit.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

@SuppressLint("ParcelCreator")
data class InnerClass(val title : String,
                      val sets: String?,
                      val reps : String?,
                      val rest : String?,
                      val time : String?,
                      val drawable: Int) : Parcelable {
    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        TODO("Not yet implemented")
    }
}