package com.yawtseb.bestway.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.airetefacruo.myapplication.api.RxExtensions.Companion.ioNewThread
import com.airetefacruo.myapplication.util.crypto.Encrypt
import com.airetefacruo.myapplication.util.network.NetWorkState
import com.airetefacruo.myapplication.util.network.Resource
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.yawtseb.bestway.R
import com.yawtseb.bestway.base.BaseViewModel
import com.yawtseb.bestway.model.AccountVo
import com.yawtseb.bestway.model.DataResult
import com.yawtseb.bestway.model.PinCodeVo
import com.yawtseb.bestway.model.TokenVo
import com.yawtseb.bestway.repository.AccountRepository
import com.yawtseb.bestway.util.ConstData
import com.yawtseb.bestway.util.ConstData.emailPattern
import com.yawtseb.bestway.util.ConstData.passwordPattern
import com.yawtseb.bestway.util.Lambda.Companion.isValid
import com.yawtseb.bestway.util.ShowMsg
import com.yawtseb.bestway.util.ShowMsg.Companion.showLog
import io.reactivex.observers.DisposableObserver

class AccountViewModel(
    private val accountRepository: AccountRepository,
    private val netWorkState: NetWorkState
): BaseViewModel() {

    // 로그인 관련 코드   =====
    private val _signInState = MutableLiveData<Resource<TokenVo>>()
    val signInState: LiveData<Resource<TokenVo>> get() = _signInState

    // 로그인
    fun signIn(account: AccountVo){
        _signInState.postValue(Resource.loading(null))

        val gson = Gson()
        val jsString = gson.toJson(account)
        val json: JsonObject = gson.fromJson(jsString, JsonObject::class.java)

        showLog(json, "account json")

        if(netWorkState.isNetworkConnected()){ // network connected
            var result = DataResult()

            /// TODO: 2021-02-01 aes로 암호화 필요
            addToDisposable(accountRepository.signIn(json).ioNewThread()
                .subscribeWith(object : DisposableObserver<DataResult>() {
                    override fun onNext(t: DataResult) {
                        showLog(t, "signIn")
                        result = t
                    }

                    override fun onError(e: Throwable) {
                        _signInState.postValue(Resource.error(e.message.toString(), null))
                        showLog("signIn error: ${e.message.toString()}")
                    }

                    override fun onComplete() {
                        when (result.status) {
                            // 로그인 성공한 상태
                            "201" -> {
                                val data = result.data
                                if (data != null) {
                                    val jsElement: JsonElement = data.get(0)
                                    val tokenJson = jsElement.asJsonObject.get("token")

                                    showLog("signIn tokenJson: $tokenJson")

                                    val tokenVo = Gson().fromJson(tokenJson, TokenVo::class.java)

                                    showLog("signIn tokenVo: $tokenVo")
                                    _signInState.postValue(Resource.success(tokenVo))
                                }
                            }

                            else -> _signInState.postValue(
                                Resource.error(
                                    result.msg.toString(),
                                    null
                                )
                            )
                        }
                    }
                })
            )
        }
        else { // network disconnected
            _signInState.postValue(Resource.errorInt(R.string.network_disconnected_and_retry, null))
        }
    }

    private val _signInFormState = MutableLiveData<SignInFormState>()
    val signInFormState: LiveData<SignInFormState> get() = _signInFormState
    data class SignInFormState(val errorEmail: Int? = null, val errorPw: Int? = null, val isFormValid: Boolean = false)

    fun setTextData(email:String, password:String){
        if(!email.isValid(emailPattern)) _signInFormState.postValue(SignInFormState(errorEmail = R.string.error_input_email))
        else if(!password.isValid(passwordPattern)) _signInFormState.postValue(SignInFormState(errorPw = R.string.error_input_password_form))
        else _signInFormState.postValue(SignInFormState(isFormValid = true))
    }
    // 로그인 관련 코드   =====

    // 회원가입 관련 코드   =====
    data class SignUpDataState(
        val errName: Int? = null,
        val errEmail: Int? = null,
        val errPassword: Int? = null,
        val errPhoneNumber: Int? = null,
        val isValid: Boolean = false
    )

    private val _signUpDataState = MutableLiveData<SignUpDataState>()
    val signUpDataState: LiveData<SignUpDataState> = _signUpDataState

    private val _inputEditText = MutableLiveData<Int>()
    val inputEditText: LiveData<Int> = _inputEditText

    fun setSignUpData(fullName: String, email: String, password: String, phoneNumber: String){
        if(!fullName.isValid(ConstData.namePattern)) _signUpDataState.postValue(SignUpDataState(errName = R.string.error_input_name))
        else if(!email.isValid(emailPattern)) _signUpDataState.postValue(SignUpDataState(errEmail = R.string.error_input_email))
        else if(!password.isValid(passwordPattern)) _signUpDataState.postValue(SignUpDataState(errPassword = R.string.error_password_format))
        else if(!phoneNumber.isValid(ConstData.phonePattern)) _signUpDataState.postValue(SignUpDataState(errPhoneNumber = R.string.error_input_phone_number))
        else _signUpDataState.postValue(SignUpDataState(isValid = true))
    }

    fun setInputEditText(id: Int){
        _inputEditText.postValue(id)
    }

    private val _signUpState = MutableLiveData<Resource<DataResult>>()
    val signUpState: LiveData<Resource<DataResult>> get() = _signUpState

    // 회원가입
    fun signUp(account: AccountVo){
        var result = DataResult()

        val gson = Gson()
        val jsString = gson.toJson(account)
        val json: JsonObject = gson.fromJson(jsString, JsonObject::class.java)

        showLog(json, "account json")

        _signUpState.postValue(Resource.loading(null))
        if(netWorkState.isNetworkConnected()){
            addToDisposable(accountRepository.singUp(json).ioNewThread()
                .subscribeWith(object : DisposableObserver<DataResult>() {
                    override fun onNext(t: DataResult) {
                        showLog(t, "signUp")
                        result = t
                    }

                    override fun onError(e: Throwable) {
                        showLog(e.message.toString())
                        _signUpState.postValue(Resource.error(e.message.toString(), null))
                    }

                    override fun onComplete() {
                        when(result.status){
                            "200" -> _signUpState.postValue(Resource.success(result))
                            else -> _signUpState.postValue(Resource.error(result.msg.toString(), null))
                        }
                    }

                }))
        }
        else{
            _signUpState.postValue(Resource.errorInt(R.string.network_disconnected_and_retry, null))
        }

    }
    // 회원가입 관련 코드   =====

    // 접근토큰 갱신   =====
    private val _updateAccessToken = MutableLiveData<Resource<DataResult>>()
    val updateAccessToken: LiveData<Resource<DataResult>> get() = _updateAccessToken

    fun updateAccessToken(accessToken: String){
        _updateAccessToken.postValue(Resource.loading(null))
        var result = DataResult()

        if(netWorkState.isNetworkConnected()){
            addToDisposable(accountRepository.updateAccessToken(accessToken = accessToken).ioNewThread()
                .subscribeWith(object : DisposableObserver<DataResult>(){
                    override fun onNext(t: DataResult) {
                        showLog(t)
                        result = t
                    }

                    override fun onError(e: Throwable) {
                        _updateAccessToken.postValue(Resource.error(e.message.toString(), null))
                    }

                    override fun onComplete() {
                        when(result.status){
                            "200" -> _updateAccessToken.postValue(Resource.success(null))
                            else -> _updateAccessToken.postValue(Resource.error(result.msg.toString(), null))
                        }
                    }

                }))
        }
        else{
            _updateAccessToken.postValue(Resource.errorInt(R.string.network_disconnected_and_retry, null))
        }
    }
    // 접근토큰 갱신   =====

    // 로그아웃   =====
    val logout = MutableLiveData<Resource<DataResult>>()

    fun logout(accessToken: String){
        logout.postValue(Resource.loading(null))
        var result = DataResult()

        if(netWorkState.isNetworkConnected()){
            addToDisposable(accountRepository.logout(accessToken = accessToken).ioNewThread()
                .subscribeWith(object : DisposableObserver<DataResult>(){
                    override fun onNext(t: DataResult) {
                        showLog(t)
                        result = t
                    }

                    override fun onError(e: Throwable) {
                        logout.postValue(Resource.error(e.message.toString(), null))
                    }

                    override fun onComplete() {
                        when(result.status){
                            "200" -> logout.postValue(Resource.success(result))
                            else -> logout.postValue(Resource.error(result.msg.toString(), null))
                        }
                    }

                }))
        }
        else{
            logout.postValue(Resource.errorInt(R.string.network_disconnected_and_retry, null))
        }
    }
    // 로그아웃   =====

    // 회원탈퇴   =====
    val withdrawal = MutableLiveData<Resource<DataResult>>()

    fun withdrawal(accessToken: String){
        withdrawal.postValue(Resource.loading(null))
        var result = DataResult()

        if(netWorkState.isNetworkConnected()){
            addToDisposable(accountRepository.withdrawal(accessToken = accessToken).ioNewThread()
                .subscribeWith(object : DisposableObserver<DataResult>(){
                    override fun onNext(t: DataResult) {
                        showLog(t)
                        result = t
                    }

                    override fun onError(e: Throwable) {
                        withdrawal.postValue(Resource.error(e.message.toString(), null))
                    }

                    override fun onComplete() {
                        when(result.status){
                            "200" -> withdrawal.postValue(Resource.success(result))
                            else -> withdrawal.postValue(Resource.error(result.msg.toString(), null))
                        }
                    }

                }))
        }
        else{
            withdrawal.postValue(Resource.errorInt(R.string.network_disconnected_and_retry, null))
        }
    }
    // 회원탈퇴   =====

    // 핀 코드 가져오기   =====
    val authUserPinCode = MutableLiveData<Resource<PinCodeVo>>()

    fun authUserPinCode(pinCode: String, accessToken: String){
        authUserPinCode.postValue(Resource.loading(null))
        showLog("pinCode: $pinCode, accessToken: $accessToken")

        val enPinCode = Encrypt.encrypt(pinCode, 5)
        var result = DataResult()

        if(netWorkState.isNetworkConnected()){
            addToDisposable(accountRepository.authUserPinCode(enPinCode, accessToken).ioNewThread()
                .subscribeWith(object: DisposableObserver<DataResult>(){
                    override fun onNext(t: DataResult) {
                        showLog(t, "getUserPinCode")
                        result = t
                    }

                    override fun onError(e: Throwable) {
                        authUserPinCode.postValue(Resource.error(e.message.toString(), null))
                    }

                    override fun onComplete() {
                        when(result.status){
                            "400"-> authUserPinCode.postValue(Resource.error(result.msg.toString(), null))
                            "201" -> {
//                                val data = result.data
//                                var pinCodeVo: PinCodeVo? = null
//                                if(data!=null){
//                                    val gson = Gson()
//                                    pinCodeVo = gson.fromJson(data[0], PinCodeVo::class.java)
//                                }
//                                authUserPinCode.postValue(Resource.success(pinCodeVo))
                            }
                            "202" -> authUserPinCode.postValue(Resource.success(null))
                        }
                    }
                }))
        }
        else{
            authUserPinCode.postValue(Resource.errorInt(R.string.network_disconnected_and_retry, null))
        }
        // 핀 코드 가져오기   =====


    }

    // 핀 코드 수정하기   =====
    val updateUserPinCode = MutableLiveData<Resource<DataResult>>()

    fun updateUserPinCode(pinCode: String, accessToken: String) {
        updateUserPinCode.postValue(Resource.loading(null))

        val enPinCode = Encrypt.encrypt(pinCode, 5)

        var result = DataResult()

        if (netWorkState.isNetworkConnected()) {
            addToDisposable(accountRepository.updateUserPinCode(enPinCode, accessToken).ioNewThread()
                .subscribeWith(object : DisposableObserver<DataResult>() {
                    override fun onNext(t: DataResult) {
                        showLog(t, "getUserPinCode")
                        result = t
                    }

                    override fun onError(e: Throwable) {
                        updateUserPinCode.postValue(Resource.error(e.message.toString(), null))
                    }

                    override fun onComplete() {
                        when (result.status) {
                            "400" -> updateUserPinCode.postValue(Resource.error(result.msg.toString(), null))
                            else -> updateUserPinCode.postValue(Resource.success(result))
                        }
                    }
                })
            )
        } else {
            updateUserPinCode.postValue(Resource.errorInt(R.string.network_disconnected_and_retry, null))
        }
    }
    // 핀 코드 수정하기   =====

    // 핀 코드 생성하기   =====
    val createUserPinCode = MutableLiveData<Resource<TokenVo>>()

    fun createUserPinCode(pinCode: String, accessToken: String) {
        createUserPinCode.postValue(Resource.loading(null))
        val enPinCode = Encrypt.encrypt(pinCode, 5)
        var result = DataResult()

        showLog("enPinCode: $enPinCode, accessToken: $accessToken")

        if (netWorkState.isNetworkConnected()) {
            addToDisposable(accountRepository.createUserPinCode(enPinCode, accessToken).ioNewThread()
                .subscribeWith(object : DisposableObserver<DataResult>() {
                    override fun onNext(t: DataResult) {
                        showLog(t, "getUserPinCode")
                        result = t
                    }

                    override fun onError(e: Throwable) {
                        createUserPinCode.postValue(Resource.error(e.message.toString(), null))
                    }

                    override fun onComplete() {
                        when (result.status) {
                            "200" -> {
                                val data = result.data
                                if(data!=null){
                                    val gson = Gson()
//                                    val token = gson.fromJson(data[0], TokenVo::class.java)
//                                    createUserPinCode.postValue(Resource.success(token))
                                }
                                else createUserPinCode.postValue(Resource.successInt(R.string.error_occur, null))

                            }
                            else -> createUserPinCode.postValue(Resource.error(result.msg.toString(), null))
                        }
                    }
                })
            )
        } else {
            createUserPinCode.postValue(Resource.errorInt(R.string.network_disconnected_and_retry, null))
        }
    }
    // 핀 코드 생성하기    =====
}