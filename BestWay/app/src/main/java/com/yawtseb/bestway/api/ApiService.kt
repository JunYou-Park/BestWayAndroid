package com.airetefacruo.myapplication.api

import com.yawtseb.bestway.model.DataResult
import com.yawtseb.bestway.util.ConstData.EVENT_API
import com.yawtseb.bestway.util.ConstData.FCM_API
import com.yawtseb.bestway.util.ConstData.MENU_API
import com.yawtseb.bestway.util.ConstData.USER_API
import io.reactivex.Observable
import retrofit2.http.*


// status 200번대 => 서버와 통신 OK
// status: 200 -> 받아올 데이터는 없는 경우
// status: 201 -> 데이터를 받는 경우
// status: 202 -> 데이터를 받고 아직 서버와 통신이 남은 경우
// status: 204 -> 세션이 만료된 경우

// status 400번대 => 서버와 통신 OK, 유효하지 않는 상태

// status: 400 -> 클라이언트의 요청이 유효하지 않음 (받아올 데이터가 없음)


// status: 500 -> 서버 자체에서 오류 발생

interface ApiService {

    @FormUrlEncoded
    @POST("${USER_API}/sign_in.do")
    fun signIn(@Field("email") email: String, @Field("pw") password: String): Observable<DataResult>

    @FormUrlEncoded
    @POST("${USER_API}/sign_up.do")
    fun signUp(@Field("fullName") fullName:String, @Field("email") email: String, @Field("pw") password: String, @Field("phone_number") phoneNumber: String, @Field("createAt") createAt: String):Observable<DataResult>

    @FormUrlEncoded
    @POST("${FCM_API}/get_notice.do")
    fun getNotifications(@Field("user_token") userToken: String? = null ): Observable<DataResult>

    @GET("${EVENT_API}/get_banner.do")
    fun getBanners(): Observable<DataResult>

    @GET("${MENU_API}/get_today_menu.do")
    fun getToDayDiet(@Query("today_menu_date") today: String): Observable<DataResult>

    @FormUrlEncoded
    @POST("${USER_API}/update_access_token.do")
    fun updateAccessToken(@Field("access_token") accessToken: String): Observable<DataResult>

    @FormUrlEncoded
    @POST("${USER_API}/logout.do")
    fun logout(@Field("access_token") accessToken: String): Observable<DataResult>

    @FormUrlEncoded
    @POST("${USER_API}/withdrawal.do")
    fun withdrawal(@Field("access_token") accessToken: String): Observable<DataResult>

    @FormUrlEncoded
    @POST("${USER_API}/get_user_ticket.do")
    fun getUserTicket(@Field("access_token") accessToken: String): Observable<DataResult>

    @FormUrlEncoded
    @POST("${USER_API}/auth_user_pin_code.do")
    fun authUserPinCode(@Field("en_pin_code") pinCode: String, @Field("access_token") accessToken: String): Observable<DataResult>

    @FormUrlEncoded
    @POST("${USER_API}/update_user_pin_code.do")
    fun updateUserPinCode(@Field("en_pin_code") pinCode: String, @Field("access_token") accessToken: String): Observable<DataResult>

    @FormUrlEncoded
    @POST("${USER_API}/create_user_pin_code.do")
    fun createUserPinCode(@Field("en_pin_code") pinCode: String, @Field("access_token") accessToken: String): Observable<DataResult>

}