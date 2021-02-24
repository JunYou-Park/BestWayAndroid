package com.yawtseb.bestway.model

import com.google.gson.annotations.SerializedName

data class TokenVo(
        @SerializedName("refresh_token")
        val refreshToken: String,

        @SerializedName("access_token")
        val accessToken: String,

        @SerializedName("user_auth")
        val userAuth: String
)