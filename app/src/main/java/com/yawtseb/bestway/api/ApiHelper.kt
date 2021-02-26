package com.airetefacruo.myapplication.api

import com.google.gson.JsonObject
import com.yawtseb.bestway.model.DataResult
import io.reactivex.Observable
import org.json.JSONObject
import retrofit2.http.Field


interface ApiHelper {
    fun signIn(data: JsonObject): Observable<DataResult>

    fun signUp(data: JsonObject): Observable<DataResult>

    fun getNotifications(userToken: String? = null ): Observable<DataResult>

    fun getBanners(): Observable<DataResult>

    fun getToDayDiet(today: Long): Observable<DataResult>

    fun updateAccessToken(accessToken: String): Observable<DataResult>

    fun getUserTicket(data: JsonObject): Observable<DataResult>

    fun logout(accessToken: String): Observable<DataResult>

    fun withdrawal(accessToken: String): Observable<DataResult>

    fun authUserPinCode(pinCode: String, accessToken: String): Observable<DataResult>

    fun updateUserPinCode(pinCode: String, accessToken: String): Observable<DataResult>

    fun createUserPinCode(pinCode: String, accessToken: String): Observable<DataResult>
}