package com.stayfit.stayfit.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

@SuppressLint("ParcelCreator")
data class PtModel(val ptName: String, val ptDesc : String, val ptUserName : String, val ptEmail : String, val ptFee : String) : Parcelable{
    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        TODO("Not yet implemented")
    }

}