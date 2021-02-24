package com.yawtseb.bestway.base

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yawtseb.bestway.R
import com.yawtseb.bestway.databinding.LayoutPinCodeDialogBinding
import com.yawtseb.bestway.listener.PinCodeClickListener
import com.yawtseb.bestway.util.SessionHelper
import com.yawtseb.bestway.util.ShowMsg
import com.yawtseb.bestway.util.ShowMsg.Companion.showLog
import com.yawtseb.bestway.viewmodel.AccountViewModel
import com.yawtseb.bestway.viewmodel.PinCodeViewModel
import org.koin.android.viewmodel.ext.android.viewModel

abstract class BasePinCodeDialog: DialogFragment()  {

    val binding by lazy { LayoutPinCodeDialogBinding.inflate(layoutInflater) }

    val sessionHelper: SessionHelper by lazy { SessionHelper.getInstance() }
    val showMsg: ShowMsg by lazy { ShowMsg(requireActivity()) }

    val pinCodeViewModel: PinCodeViewModel by lazy { ViewModelProvider(requireActivity()).get(PinCodeViewModel::class.java) }
    val accountViewModel: AccountViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setImagePinCode()

        setRandomPinCode()

        setObserver()

        // 로딩뷰 생성
        binding.loadingView.create()

        binding.ibPinCodeClose.setOnClickListener {
            this.dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
//        초기화 안 시키면 다시 시작할때, 값이 그대로 남아있음
        setRandomPinCode()
        pinCodeViewModel.resetState()
    }

    private fun setImagePinCode() {
        binding.pccPinCodeList.createCircleList(6, R.drawable.selector_circle_pin_code)
    }

    fun setRandomPinCode() {
        binding.pcpPinCodeTable.createPad()

        binding.pcpPinCodeTable.setPinCodeClickListener(object : PinCodeClickListener{
            override fun onPinCodeClick(tag: String) {
                when(tag){
                    // 초기화 버튼
                    "reset" -> {
                        setRandomPinCode()
                        pinCodeViewModel.resetPinCode()
                    }

                    // 백스페이스 버튼
                    "del" -> { pinCodeViewModel.delPinCode() }

                    // 입력값 버튼
                    else ->{ pinCodeViewModel.addPinCode(tag) }
                }
            }
        })
    }

    abstract fun setObserver()

}