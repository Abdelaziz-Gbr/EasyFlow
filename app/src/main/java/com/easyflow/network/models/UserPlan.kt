package com.easyflow.network.models

import android.os.Parcel
import android.os.Parcelable

data class UserPlan (
    var repurchase: Boolean,
    val remainingTrips: Int,
    val planOwnerName: String,
    val planName: String,
    val expireDate: String
        ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (repurchase) 1 else 0)
        parcel.writeInt(remainingTrips)
        parcel.writeString(planOwnerName)
        parcel.writeString(planName)
        parcel.writeString(expireDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserPlan> {
        override fun createFromParcel(parcel: Parcel): UserPlan {
            return UserPlan(parcel)
        }

        override fun newArray(size: Int): Array<UserPlan?> {
            return arrayOfNulls(size)
        }
    }

}
fun UserPlan.getRepurchasReveresed(): UserPlan{
    return UserPlan(!repurchase, remainingTrips,planOwnerName,planName,expireDate)
}