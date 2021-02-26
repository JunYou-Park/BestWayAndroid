package com.yawtseb.bestway.ui.frag.singup

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Button
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.airetefacruo.myapplication.util.crypto.Encrypt
import com.airetefacruo.myapplication.util.email.SendMail
import com.airetefacruo.myapplication.util.network.Status
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.yawtseb.bestway.R
import com.yawtseb.bestway.base.BaseFrag
import com.yawtseb.bestway.databinding.FragmentSignUpBinding
import com.yawtseb.bestway.model.AccountVo
import com.yawtseb.bestway.util.AnimationHelper.Companion.bottomPushDownAnimation
import com.yawtseb.bestway.util.AnimationHelper.Companion.bottomPushUpAnimation
import com.yawtseb.bestway.util.AnimationHelper.Companion.fadeInAnimation
import com.yawtseb.bestway.util.AnimationHelper.Companion.fadeOutAnimation
import com.yawtseb.bestway.util.AnimationHelper.Companion.pushDownAnimation
import com.yawtseb.bestway.util.AnimationHelper.Companion.pushUpAnimation
import com.yawtseb.bestway.util.ConstData.AUTH_URL
import com.yawtseb.bestway.util.ConstData.SIGNUP_ANIM_TIME
import com.yawtseb.bestway.viewmodel.AccountViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class SignUpFrag : BaseFrag(), View.OnTouchListener {

    private val binding: FragmentSignUpBinding by lazy { FragmentSignUpBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController

    private val signUpViewModel: AccountViewModel by viewModel()

    private var inputTextEditId = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        setOnClickListener()

        setTextWatcher()

        setObserver()

        binding.loadingView.create()

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setOnClickListener(){
        binding.btnSignUp.setOnClickListener {
            val account = AccountVo(user_email = binding.etSignUpEmail.text.toString(), user_pw = binding.etSignUpPassword.text.toString(),
            user_full_name = binding.etSignUpName.text.toString(), user_phone_number = binding.etSignUpPhone.text.toString(),
            user_pin_code = "0", user_create_time = Date().time)

            signUpViewModel.signUp(account)
        }

        binding.etSignUpEmail.setOnTouchListener(this)
        binding.etSignUpPassword.setOnTouchListener(this)
        binding.etSignUpPhone.setOnTouchListener(this)
    }

    private fun setTextWatcher(){
        binding.etSignUpName.basicTextWatcher {
            setSignUpData(binding.etSignUpName.id)
        }

        binding.etSignUpEmail.basicTextWatcher {
            setSignUpData(binding.etSignUpEmail.id)
        }

        binding.etSignUpPassword.basicTextWatcher {
            setSignUpData(binding.etSignUpPassword.id)
        }

        binding.etSignUpPhone.apply {
            basicTextWatcher {
                setSignUpData(binding.etSignUpPhone.id)
            }
            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> binding.btnSignUp.performClick()
                }
                false
            }
        }

    }

    private fun setObserver(){

        signUpViewModel.inputEditText.observe(viewLifecycleOwner, {
            inputTextEditId = it
        })

        signUpViewModel.signUpDataState.observe(viewLifecycleOwner, {
            val state = it ?: return@observe
            if (state.errName != null) {
                binding.elSignUpName.error = getString(state.errName)
                binding.tvSignUpTitle.text = getString(R.string.plz_input_name)
                if (binding.btnSignUp.visibility == View.VISIBLE) {
                    fadeOutAnimation(binding.btnSignUp)
                    bottomPushDownAnimation(binding.btnSignUp)
                }

                if (binding.elSignUpPhone.visibility == View.VISIBLE) {
                    fadeOutAnimation(binding.elSignUpPhone)
                    pushUpAnimation(binding.elSignUpPhone)
                }

                if (binding.elSignUpPassword.visibility == View.VISIBLE) {
                    fadeOutAnimation(binding.elSignUpPassword)
                    pushUpAnimation(binding.elSignUpPassword)
                }

                if (binding.elSignUpEmail.visibility == View.VISIBLE) {
                    fadeOutAnimation(binding.elSignUpEmail)
                    pushUpAnimation(binding.elSignUpEmail)
                }
            } else {
                binding.elSignUpName.error = null
                if (binding.elSignUpEmail.visibility != View.VISIBLE) {
                    fadeInAnimation(binding.elSignUpEmail, SIGNUP_ANIM_TIME)
                    pushDownAnimation(binding.elSignUpEmail)
                }
            }
            if (state.errEmail != null) {
                binding.tvSignUpTitle.text = getString(R.string.plz_input_id_email)
                binding.elSignUpEmail.error = getString(state.errEmail)
                if (binding.btnSignUp.visibility == View.VISIBLE) {
                    fadeOutAnimation(binding.btnSignUp)
                    bottomPushDownAnimation(binding.btnSignUp)
                }

                if (binding.elSignUpPhone.visibility == View.VISIBLE) {
                    fadeOutAnimation(binding.elSignUpPhone)
                    pushUpAnimation(binding.elSignUpPhone)
                }

                if (binding.elSignUpPassword.visibility == View.VISIBLE) {
                    fadeOutAnimation(binding.elSignUpPassword)
                    pushUpAnimation(binding.elSignUpPassword)
                }

            } else {
                binding.elSignUpEmail.error = null
                // 이메일 창이 보이고 패스워드 창이 없으면서 현재 입력하고 있는 입력 창이 etEmail인 경우
                if (binding.elSignUpEmail.visibility == View.VISIBLE && binding.elSignUpPassword.visibility != View.VISIBLE && inputTextEditId == binding.etSignUpEmail.id) {
                    fadeInAnimation(binding.elSignUpPassword, SIGNUP_ANIM_TIME)
                    pushDownAnimation(binding.elSignUpPassword)
                }
            }
            if (state.errPassword != null) {
                binding.tvSignUpTitle.text = getString(R.string.plz_input_password)
                binding.elSignUpPassword.error = getString(state.errPassword)

                if (binding.btnSignUp.visibility == View.VISIBLE) {
                    fadeOutAnimation(binding.btnSignUp)
                    bottomPushDownAnimation(binding.btnSignUp)
                }

                if (binding.elSignUpPhone.visibility == View.VISIBLE) {
                    fadeOutAnimation(binding.elSignUpPhone)
                    pushUpAnimation(binding.elSignUpPhone)
                }

            } else {
                binding.elSignUpPassword.error = null
                if (binding.elSignUpPassword.visibility == View.VISIBLE && binding.elSignUpPhone.visibility != View.VISIBLE && inputTextEditId == binding.etSignUpPassword.id) {
                    fadeInAnimation(binding.elSignUpPhone, SIGNUP_ANIM_TIME)
                    pushDownAnimation(binding.elSignUpPhone)
                }
            }

            if(state.errPhoneNumber!=null){
                binding.tvSignUpTitle.text = getString(R.string.plz_input_phone_number)
                binding.elSignUpPhone.error = getString(state.errPhoneNumber)
                if (binding.btnSignUp.visibility == View.VISIBLE) {
                    fadeOutAnimation(binding.btnSignUp)
                    bottomPushDownAnimation(binding.btnSignUp)
                }
            }
            else{
                binding.elSignUpPhone.error = null
                if (binding.elSignUpPhone.visibility == View.VISIBLE && binding.btnSignUp.visibility != View.VISIBLE && inputTextEditId == binding.etSignUpPhone.id) {
                    fadeInAnimation(binding.btnSignUp, SIGNUP_ANIM_TIME)
                    bottomPushUpAnimation(binding.btnSignUp)
                }
            }

            if(state.isValid)   binding.tvSignUpTitle.text = getString(R.string.plz_click_sign_up)
        })

        signUpViewModel.signUpState.observe(viewLifecycleOwner, Observer {
            val resource = it ?: return@Observer

            loading()

            when (resource.status) {
                Status.SUCCESS -> {
                    val sendMail = SendMail()
                    CoroutineScope(Dispatchers.IO).launch {
                        sendMail.sendSecurityCode(subject = "BestWay 이메일 인증입니다.", sendTo = binding.etSignUpEmail.text.toString(), "${AUTH_URL}${Encrypt.encrypt(binding.etSignUpEmail.text.toString(), 5)}", "default")
                    }

                    val dialog = showMsg.showOneTitleOneButtonDialog(title = getString(R.string.email_authentication_title), button = getString(R.string.confirm))
                    dialog.findViewById<Button>(R.id.btn_obot_button).setOnClickListener {
                        dialog.dismiss()
                    }
                    dialog.setOnDismissListener {
                        navController.popBackStack()
                    }
                    dialog.show()

                }
                Status.ERROR_INT -> {
                    val dialog = showMsg.showOneTitleOneButtonDialog(title = getString(resource.intMsg!!), button = getString(R.string.confirm))
                    dialog.setOnDismissListener {
                        navController.popBackStack()
                    }
                }
                Status.ERROR -> {
                    showMsg.showShortToastMsg(resource.message.toString())
                }
                else -> return@Observer
            }
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            setSignUpData(v.id)
        }
        return false
    }

    private fun setSignUpData(id: Int){
        signUpViewModel.setInputEditText(id)
        signUpViewModel.setSignUpData(fullName = binding.etSignUpName.text.toString(),
            email = binding.etSignUpEmail.text.toString(), password = binding.etSignUpPassword.text.toString(),
            phoneNumber = binding.etSignUpPhone.text.toString())
    }

    private fun loading(){
        binding.loadingView.isVisible()

        for(i in 0 until binding.constSignUp.childCount){
            val obj = binding.constSignUp.getChildAt(i)
            if(obj is TextInputLayout || obj is Button || obj is TextInputEditText){
                obj.isEnabled = !obj.isEnabled
            }
        }
    }


}