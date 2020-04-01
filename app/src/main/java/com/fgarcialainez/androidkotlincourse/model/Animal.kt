package com.fgarcialainez.androidkotlincourse.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ApiKey(
    val key: String?,
    val message: String?
)

data class Speed(
    val metric: String?,
    val imperial: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(metric)
        parcel.writeString(imperial)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Speed> {
        override fun createFromParcel(parcel: Parcel): Speed {
            return Speed(parcel)
        }

        override fun newArray(size: Int): Array<Speed?> {
            return arrayOfNulls(size)
        }
    }
}

data class Taxonomy(
    val order: String?,
    val family: String?,
    val kingdom: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(order)
        parcel.writeString(family)
        parcel.writeString(kingdom)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Taxonomy> {
        override fun createFromParcel(parcel: Parcel): Taxonomy {
            return Taxonomy(parcel)
        }

        override fun newArray(size: Int): Array<Taxonomy?> {
            return arrayOfNulls(size)
        }
    }
}

data class Animal(
    val name: String?,
    val diet: String?,
    val speed: Speed?,
    val location: String?,
    val taxonomy: Taxonomy?,

    @SerializedName("lifespan")
    val lifeSpan: String?,

    @SerializedName("image")
    val imageUrl: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Speed::class.java.classLoader),
        parcel.readString(),
        parcel.readParcelable(Taxonomy::class.java.classLoader),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(diet)
        parcel.writeParcelable(speed, flags)
        parcel.writeString(location)
        parcel.writeParcelable(taxonomy, flags)
        parcel.writeString(lifeSpan)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Animal> {
        override fun createFromParcel(parcel: Parcel): Animal {
            return Animal(parcel)
        }

        override fun newArray(size: Int): Array<Animal?> {
            return arrayOfNulls(size)
        }
    }
}

data class AnimalPalette(var color: Int)