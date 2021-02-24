package com.airetefacruo.myapplication.api

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override fun signIn(email: String, password: String) = apiService.signIn(email, password)

    override fun signUp(fullName: String, email: String, password: String, phoneNumber:String, createAt: String) = apiService.signUp(fullName, email, password, phoneNumber, createAt)

    override fun getNotifications(userToken: String?) = apiService.getNotifications(userToken)

    override fun getBanners() = apiService.getBanners()

    override fun getToDayDiet(today: String) = apiService.getToDayDiet(today)

    override fun updateAccessToken(accessToken: String) = apiService.updateAccessToken(accessToken)

    override fun getUserTicket(accessToken: String) = apiService.getUserTicket(accessToken)

    override fun logout(accessToken: String) = apiService.logout(accessToken)

    override fun withdrawal(accessToken: String) = apiService.withdrawal(accessToken)

    override fun authUserPinCode(pinCode: String, accessToken: String) = apiService.authUserPinCode(pinCode, accessToken)

    override fun updateUserPinCode(pinCode: String, accessToken: String) = apiService.updateUserPinCode(pinCode, accessToken)

    override fun createUserPinCode(pinCode: String, accessToken: String) = apiService.createUserPinCode(pinCode, accessToken)

}