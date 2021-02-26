package com.yawtseb.bestway.repository

import com.airetefacruo.myapplication.api.ApiHelper
import com.google.gson.JsonObject
import org.json.JSONObject

class DietRepository(private val apiHelper: ApiHelper) {

    fun getToDayDiet(today: Long) = apiHelper.getToDayDiet(today)

    fun getUserTicket(data: JsonObject) = apiHelper.getUserTicket(data)
}