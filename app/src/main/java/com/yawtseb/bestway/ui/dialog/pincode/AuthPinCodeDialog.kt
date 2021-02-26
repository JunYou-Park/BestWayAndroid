package com.yawtseb.bestway.ui.dialog.pincode

import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.airetefacruo.myapplication.util.network.Status
import com.yawtseb.bestway.R
import com.yawtseb.bestway.base.BasePinCodeDialog

class AuthPinCodeDialog(val result: (Boolean) -> Unit): BasePinCodeDialog() {

    override fun setObserver() {
        pinCodeViewModel.mPinCode.observe(viewLifecycleOwner, {pinCode->
            binding.pccPinCodeList.updateCircle(pinCode.length)
            if(pinCode.length == 6){
                accountViewModel.authUserPinCode(pinCode = pinCode, accessToken = sessionHelper.tokenVo!!.userAccessToken)

                pinCodeViewModel.resetState()
                setRandomPinCode()
            }
        })

        accountViewModel.authUserPinCode.observe(viewLifecycleOwner, Observer{
            val resource = it ?: return@Observer

            binding.loadingView.isVisible()

            when(resource.status){
                Status.SUCCESS ->{
                    result(true)
                    dismiss()
                }
                Status.ERROR ->{
                    binding.tvPinCodeTitle.text = resource.message.toString()
                    binding.tvPinCodeTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.holo_red_dark))
                }
                Status.ERROR_INT->{
                    result(false)
                    dismiss()
                    showMsg.showShortToastMsg(getString(resource.intMsg!!))
                }
                else -> return@Observer
            }

            accountViewModel.authUserPinCode.postValue(null)


        })
    }
}