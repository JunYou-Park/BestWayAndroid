package com.yawtseb.bestway.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yawtseb.bestway.base.BaseViewModel

class PinCodeViewModel(): BaseViewModel() {

    var isFirst: Boolean = true
    var inputFirst = ""

    private val _mPinCode = MutableLiveData<String>("")
    val mPinCode: LiveData<String> = _mPinCode

    // 맨 뒤에 핀 코드를 추가
    fun addPinCode(pin: String){
        _mPinCode.postValue(_mPinCode.value + pin)
    }

    // 맨 뒤에 핀 코드를 삭제
    fun delPinCode(){
        if(mPinCode.value!=null || mPinCode.value != "" ){
            val pin = mPinCode.value!!
            if(pin.isNotEmpty() || pin.isNotBlank()) _mPinCode.postValue(pin.substring(0, pin.length-1))
        }
    }

    // 모든 상태를 초기화
    fun resetState(){
        _mPinCode.postValue("")
        inputFirst = ""
        isFirst  = true
    }

    // 핀 코드만 초기화
    fun resetPinCode(){
        _mPinCode.postValue("")
    }

    // 처음 입력이 끝난 상태
    fun notFirst(pinCode: String){
        inputFirst = pinCode
        isFirst  = false
    }

}