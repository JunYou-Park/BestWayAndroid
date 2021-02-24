package com.airetefacruo.myapplication.api

import com.yawtseb.bestway.model.DataResult
import io.reactivex.Observable
import retrofit2.http.Field


interface ApiHelper {
    fun signIn(email: String, password: String): Observable<DataResult>

    fun signUp(fullName: String, email: String, password: String, phoneNumber:String, createAt: String): Observable<DataResult>

    fun getNotifications(userToken: String? = null ): Observable<DataResult>

    fun getBanners(): Observable<DataResult>

    fun getToDayDiet(today :String): Observable<DataResult>

    fun updateAccessToken(accessToken: String): Observable<DataResult>

    fun getUserTicket(accessToken: String): Observable<DataResult>

    fun logout(accessToken: String): Observable<DataResult>

    fun withdrawal(accessToken: String): Observable<DataResult>

    fun authUserPinCode(pinCode: String, accessToken: String): Observable<DataResult>

    fun updateUserPinCode(pinCode: String, accessToken: String): Observable<DataResult>

    fun createUserPinCode(pinCode: String, accessToken: String): Observable<DataResult>
}