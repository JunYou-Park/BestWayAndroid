package com.yawtseb.bestway.model

import com.google.gson.annotations.SerializedName

data class TokenVo(
        @SerializedName("user_refresh_token")
        val userRefreshToken: String,

        @SerializedName("user_access_token")
        val userAccessToken: String,
)