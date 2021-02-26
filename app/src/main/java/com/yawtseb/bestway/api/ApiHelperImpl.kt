package com.airetefacruo.myapplication.api

import com.google.gson.JsonObject
import org.json.JSONObject

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override fun signIn(data: JsonObject) = apiService.signIn(data)

    override fun signUp(data: JsonObject) = apiService.signUp(data)

    override fun getNotifications(userToken: String?) = apiService.getNotifications(userToken)

    override fun getBanners() = apiService.getBanners()

    override fun getToDayDiet(today: Long) = apiService.getToDayDiet(today)

    override fun updateAccessToken(accessToken: String) = apiService.updateAccessToken(accessToken)

    override fun getUserTicket(data: JsonObject) = apiService.getUserTicket(data)

    override fun logout(accessToken: String) = apiService.logout(accessToken)

    override fun withdrawal(accessToken: String) = apiService.withdrawal(accessToken)

    override fun authUserPinCode(pinCode: String, accessToken: String) = apiService.authUserPinCode(pinCode, accessToken)

    override fun updateUserPinCode(pinCode: String, accessToken: String) = apiService.updateUserPinCode(pinCode, accessToken)

    override fun createUserPinCode(pinCode: String, accessToken: String) = apiService.createUserPinCode(pinCode, accessToken)

}