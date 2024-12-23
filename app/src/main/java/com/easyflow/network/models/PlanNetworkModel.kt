package com.easyflow.network.models

import android.os.Parcel
import android.os.Parcelable


data class PlanNetworkModel(
    val name: String,
    val ownerName: String,
    val price: Float,
    val discountRate: Float,
    val durationDays: Int,
    val maxCompanion: String,
    val numberOfTrips: Int,
    val available: Boolean,
    val privilegeName: String
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(ownerName)
        parcel.writeFloat(price)
        parcel.writeFloat(discountRate)
        parcel.writeInt(durationDays)
        parcel.writeString(maxCompanion)
        parcel.writeInt(numberOfTrips)
        parcel.writeByte(if (available) 1 else 0)
        parcel.writeString(privilegeName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlanNetworkModel> {
        override fun createFromParcel(parcel: Parcel): PlanNetworkModel {
            return PlanNetworkModel(parcel)
        }

        override fun newArray(size: Int): Array<PlanNetworkModel?> {
            return arrayOfNulls(size)
        }
    }
}