package com.yawtseb.bestway.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.Button
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.airetefacruo.myapplication.util.network.Status
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.yawtseb.bestway.R
import com.yawtseb.bestway.base.BaseBottomSheetDialog
import com.yawtseb.bestway.databinding.DialogSignInBinding
import com.yawtseb.bestway.listener.SignInListener
import com.yawtseb.bestway.model.AccountVo
import com.yawtseb.bestway.model.TokenVo
import com.yawtseb.bestway.util.ShowMsg.Companion.showLog
import com.yawtseb.bestway.viewmodel.AccountViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class SignInDialog() : BaseBottomSheetDialog() {

    private val binding by lazy { DialogSignInBinding.inflate(layoutInflater) }
    private var signInListener: SignInListener? = null

    private val signInViewModel: AccountViewModel by viewModel()

    fun setSignInListener(listener: SignInListener){
        signInListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        if(dialog is BottomSheetDialog){
            dialog.behavior.skipCollapsed = true
            dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        setTextWatcher()

        setOnClickListener()

        setObserver()
    }


    private fun setTextWatcher(){

        binding.tieSignInId.basicTextWatcher {
            setTextData()
        }

        binding.tieSignInPw.apply {
            basicTextWatcher {
                setTextData()

            }
            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        binding.btnSignInLogin.performClick()
                }
                false
            }
        }
    }

    private fun setOnClickListener(){
        binding.btnSignInLogin.setOnClickListener {
            val account = AccountVo(user_email = binding.tieSignInId.text.toString(), user_pw = binding.tieSignInPw.text.toString())
            signInViewModel.signIn(account)
        }
        binding.tvSignInGoSignUp.setOnClickListener {
            dismiss()
            signInListener?.onSignUp()
        }
    }

    private fun setObserver(){
        signInViewModel.signInFormState.observe(viewLifecycleOwner, Observer{
            val state = it ?: return@Observer

            binding.btnSignInLogin.isEnabled = state.isFormValid

            if(state.errorEmail!=null) binding.tilSignInId.error = getString(state.errorEmail)
            else binding.tilSignInId.error = null
            if(state.errorPw!=null) binding.tilSignInPw.error = getString(state.errorPw)
            else binding.tilSignInPw.error = null
        })

        signInViewModel.signInState.observe(viewLifecycleOwner, Observer{
            val resource = it ?: return@Observer

            loading()

            when(resource.status){
                Status.SUCCESS ->{
                    // 로그인 성공
                    showMsg.showShortToastMsg("로그인 성공")
                    signInListener?.onLogin(resource.data!!)
                }
                Status.ERROR ->{
                    showMsg.showShortToastMsg(resource.message.toString())
                }
                Status.ERROR_INT ->{
                    showMsg.showShortToastMsg(getString(resource.intMsg!!))
                }
                Status.LOADING ->{
                }
                else -> return@Observer
            }
        })
    }

    private fun setTextData(){
        signInViewModel.setTextData(binding.tieSignInId.text.toString(), binding.tieSignInPw.text.toString())
    }

    private fun loading(){
        binding.pbSignInLoading.isVisible = !binding.pbSignInLoading.isVisible

        for(i in 0 until binding.constSignIn.childCount){
            val obj = binding.constSignIn.getChildAt(i)
            if(obj is TextInputLayout || obj is Button || obj is TextInputEditText){
                obj.isEnabled = !obj.isEnabled
            }
        }
    }
}