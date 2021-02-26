package com.yawtseb.bestway.model

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import org.json.JSONArray
import org.json.JSONObject

data class DataResult(
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("msg")
    var msg: String? = null,
    @SerializedName("data")
    var data: JsonArray? = null
)