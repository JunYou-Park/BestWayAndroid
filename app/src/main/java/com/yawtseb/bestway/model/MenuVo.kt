package com.yawtseb.bestway.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MenuVo(
    @SerializedName("menu_id")
    val menu_id: String,

    @SerializedName("menu_name")
    val menu_name: String,

    @SerializedName("menu_summary")
    val menu_summary: String? = null,

    @SerializedName("menu_price")
    val menu_price: String,

    @SerializedName("menu_thumb")
    val menu_thumb: String? = null,

    @SerializedName("menu_create_at")
    val menu_created_at: String
): Parcelable

@Parcelize
data class OrderMenuVo(
    @SerializedName("menu_id")
    val menu_id: String,

    @SerializedName("menu_amount")
    val menu_amount: Int
): Parcelable