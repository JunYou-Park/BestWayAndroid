package com.yawtseb.bestway.repository

import com.airetefacruo.myapplication.api.ApiHelper
import com.google.gson.JsonObject
import com.yawtseb.bestway.model.DataResult
import io.reactivex.Observable

class AccountRepository(private val apiHelper: ApiHelper) {

    fun signIn(data: JsonObject) = apiHelper.signIn(data)

    fun singUp(data: JsonObject) = apiHelper.signUp(data)

    fun updateAccessToken(accessToken: String) = apiHelper.updateAccessToken(accessToken)

    fun logout(accessToken: String) = apiHelper.logout(accessToken)

    fun withdrawal(accessToken: String) = apiHelper.withdrawal(accessToken)

    fun authUserPinCode(pinCode: String, accessToken: String) = apiHelper.authUserPinCode(pinCode, accessToken)

    fun updateUserPinCode(pinCode: String, accessToken: String) = apiHelper.updateUserPinCode(pinCode, accessToken)

    fun createUserPinCode(pinCode: String, accessToken: String) = apiHelper.createUserPinCode(pinCode, accessToken)
}