package com.yawtseb.bestway.ui.dialog.pincode

import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.airetefacruo.myapplication.util.network.Status
import com.yawtseb.bestway.R
import com.yawtseb.bestway.base.BasePinCodeDialog
import com.yawtseb.bestway.util.ShowMsg

class CreatePinCodeDialog(val result: (Boolean)-> Unit): BasePinCodeDialog() {

    override fun setObserver(){
        pinCodeViewModel.mPinCode.observe(viewLifecycleOwner, Observer {
            binding.pccPinCodeList.updateCircle(it.length)

            if (pinCodeViewModel.isFirst) { // 핀 코드를 처음한 상태
                if (it.length == 6) {
                    // 첫 핀 코드 입력이 아닌 상태로 변환
                    pinCodeViewModel.notFirst(it)
                    // 핀 코드 초기화
                    pinCodeViewModel.resetPinCode()
                    setRandomPinCode()

                    binding.tvPinCodeTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.black_4e))
                    binding.tvPinCodeTitle.text = getString(R.string.re_plz_input_pin_code)
                }
            } else { // 두 번째 핀 코드 입력 상태
                if(it.length == 6){
                    // 첫 입력 받은 핀 코드를 먼저 저장
                    val inputFirst = pinCodeViewModel.inputFirst

                    // 핀 코드 다이얼로그의 모든 상태값을 리셋
                    pinCodeViewModel.resetState()
                    setRandomPinCode()

                    ShowMsg.showLog("pincode: $it inputFirst: $inputFirst")

                    // 두 번째 핀 코드 입력이 처음 입력한 핀 코드와 일치한 경우
                    if (it == inputFirst) {
                        accountViewModel.createUserPinCode(pinCode = it, accessToken = sessionHelper.tokenVo!!.userAccessToken)
                    }

                    // 두 번째 핀 코드 입력값이 처음 입력한 핀 코드와 일치하지 않은 경우
                    else {
                        binding.tvPinCodeTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.holo_red_dark))
                        binding.tvPinCodeTitle.text = (getString(R.string.error_equal_pin_code))
                    }
                }

            }
        })

        accountViewModel.createUserPinCode.observe(viewLifecycleOwner, Observer{
            val resource = it ?: return@Observer

            binding.loadingView.isVisible()

            when(resource.status){
                Status.SUCCESS ->{
                    result(true)
                }
                Status.ERROR ->{
                    result(false)
                    showMsg.showShortToastMsg(resource.message.toString())
                }
                Status.ERROR_INT->{
                    result(false)
                    showMsg.showShortToastMsg(getString(resource.intMsg!!))
                }
                else -> return@Observer
            }

            accountViewModel.createUserPinCode.postValue(null)

            dismiss()
        })
    }

}