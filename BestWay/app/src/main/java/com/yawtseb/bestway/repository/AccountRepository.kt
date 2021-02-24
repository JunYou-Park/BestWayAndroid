package com.yawtseb.bestway.repository

import com.airetefacruo.myapplication.api.ApiHelper
import com.yawtseb.bestway.model.DataResult
import io.reactivex.Observable

class AccountRepository(private val apiHelper: ApiHelper) {

    fun signIn(email: String, password: String) = apiHelper.signIn(email, password)

    fun singUp(fullName: String, email: String, password: String, phoneNumber: String, createAt: String) = apiHelper.signUp(fullName, email, password, phoneNumber, createAt)

    fun updateAccessToken(accessToken: String) = apiHelper.updateAccessToken(accessToken)

    fun logout(accessToken: String) = apiHelper.logout(accessToken)

    fun withdrawal(accessToken: String) = apiHelper.withdrawal(accessToken)

    fun authUserPinCode(pinCode: String, accessToken: String) = apiHelper.authUserPinCode(pinCode, accessToken)

    fun updateUserPinCode(pinCode: String, accessToken: String) = apiHelper.updateUserPinCode(pinCode, accessToken)

    fun createUserPinCode(pinCode: String, accessToken: String) = apiHelper.createUserPinCode(pinCode, accessToken)
}