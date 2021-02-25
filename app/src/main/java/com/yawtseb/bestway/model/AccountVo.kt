package com.yawtseb.bestway.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class AccountVo (
    @SerializedName("user_email")
    val user_email: String? = null,

    @SerializedName("user_pw")
    val user_pw: String? = null,

    @SerializedName("user_full_name")
    val user_full_name: String? = null,

    @SerializedName("user_phone_number")
    val user_phone_number: String? = null,

    @SerializedName("user_pin_code")
    val user_pin_code: String? = null,

    @SerializedName("user_create_time")
    val user_create_time: Long? = null
)