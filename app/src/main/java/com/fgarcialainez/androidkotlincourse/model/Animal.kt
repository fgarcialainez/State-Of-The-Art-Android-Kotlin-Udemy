package com.fgarcialainez.androidkotlincourse.model

import com.google.gson.annotations.SerializedName

data class ApiKey(
    val key: String?,
    val message: String?
)

data class Speed(
    val metric: String?,
    val imperial: String?
)

data class Taxonomy(
    val order: String?,
    val family: String?,
    val kingdom: String?
)

data class Animal(
    val name: String?,
    val diet: String,
    val speed: Speed,
    val location: String?,
    val taxonomy: Taxonomy?,

    @SerializedName("lifespan")
    val lifeSpan: String?,

    @SerializedName("image")
    val imageUrl: String?
)