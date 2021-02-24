package com.yawtseb.bestway.repository

import com.airetefacruo.myapplication.api.ApiHelper

class DietRepository(private val apiHelper: ApiHelper) {

    fun getToDayDiet(today: String) = apiHelper.getToDayDiet(today)

    fun getUserTicket(accessToken: String) = apiHelper.getUserTicket(accessToken)
}