package com.yawtseb.bestway.ui.act

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.airetefacruo.myapplication.util.network.Status
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yawtseb.bestway.R
import com.yawtseb.bestway.listener.SignInListener
import com.yawtseb.bestway.model.TokenVo
import com.yawtseb.bestway.ui.dialog.SignInDialog
import com.yawtseb.bestway.ui.dialog.pincode.AuthPinCodeDialog
import com.yawtseb.bestway.ui.dialog.pincode.CreatePinCodeDialog
import com.yawtseb.bestway.util.SessionHelper
import com.yawtseb.bestway.util.ShowMsg
import com.yawtseb.bestway.util.setupWithNavController
import com.yawtseb.bestway.viewmodel.AccountViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainAct : AppCompatActivity() {

    private val showBottomNavFrags = arrayListOf(R.id.homeFrag, R.id.dietFrag, R.id.mealTicketFrag, R.id.accountFrag)
    private val showMsg: ShowMsg by lazy { ShowMsg(this) }
    private val sessionHelper: SessionHelper by lazy { SessionHelper.getInstance() }
    private val accountViewModel: AccountViewModel by viewModel()

    private val signInNotificationDialog: Dialog by lazy { showMsg.showOneTitleTwoButtonDialog(getString(R.string.need_sign_in), getString(R.string.cancel), getString(R.string.confirm)) }
    private val pinCodeNotificationDialog: Dialog by lazy { showMsg.showOneTitleTwoButtonDialog(getString(R.string.need_pin_code_make_pin_code), getString(R.string.cancel), getString(R.string.confirm)) }

    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) setUpBottomNavigationBar()

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setUpBottomNavigationBar()
    }

    private fun setUpBottomNavigationBar(){
        bottomNavigationView = findViewById(R.id.bottom_nav)
        val navGraphIds = listOf(R.navigation.navigation_home, R.navigation.navigation_diet, R.navigation.navigation_meal_ticket, R.navigation.navigation_account)

        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds, supportFragmentManager, R.id.nav_host_container, intent
        )

        controller.observe(this, Observer { navController ->
            mNavController = navController
            navController.addOnDestinationChangedListener { _, destination, _ ->
                if(showBottomNavFrags.contains(destination.id)) bottomNavigationView.visibility = View.VISIBLE
                else bottomNavigationView.visibility = View.GONE

            }
        })

    }

    fun showLoginDialog(onResult: (String, TokenVo?) -> Unit){
        val dialog = SignInDialog()
        dialog.show(supportFragmentManager, "SignIn_Dialog")
        dialog.setSignInListener(object : SignInListener {
            override fun onSignUp() {
                showMsg.showShortToastMsg("회원가입 클릭")
                if(mNavController.graph.id == R.id.home) mNavController.navigate(R.id.signUpFrag)
                else if(mNavController.graph.id == R.id.account) mNavController.navigate(R.id.signUpFrag_account)
            }

            override fun onLogin(tokenVo: TokenVo) {
                // 토큰을 저장
                sessionHelper.setToken(tokenVo = tokenVo)
                // 성공 로직을 보내줌
                onResult("success", null)
            }

            override fun onDisConnection(tokenVo: TokenVo) {
                // 이미 로그인되어 있는 상태인걸 리턴
                onResult("disconnect", tokenVo)
            }

        })
    }

    fun showSessionDialog(tokenVo: TokenVo, onState:(Boolean)->Unit){
        val sessionDialog = showMsg.showOneTitleTwoButtonDialog(getString(R.string.already_sign_in_sign_out), getString(R.string.cancel), getString(R.string.confirm))
        sessionDialog.show()
        sessionDialog.findViewById<Button>(R.id.btn_tbot_1).setOnClickListener {
            sessionDialog.dismiss()
        }
        sessionDialog.findViewById<Button>(R.id.btn_tbot_2).setOnClickListener {
            sessionDialog.dismiss()

            accountViewModel.updateAccessToken(tokenVo.accessToken)

            accountViewModel.updateAccessToken.observe(this, Observer {
                val resource = it ?: return@Observer

                when(resource.status){
                    Status.SUCCESS -> {
                        showMsg.showShortToastMsg("기존 세션을 만료시켰습니다.")
                        // 토큰을 저장
                        sessionHelper.setToken(tokenVo = tokenVo)
                        onState(true)
                    }
                    Status.ERROR ->{
                        showMsg.showShortToastMsg(resource.message.toString())
                        onState(false)
                    }
                    Status.ERROR_INT -> {
                        showMsg.showShortToastMsg(getString(resource.intMsg!!))
                        onState(false)
                    }
                    else -> return@Observer
                }
            })
        }
    }

    // 로그인 해야하는 경우 생성되는 다이얼로그
    fun showSignInNotificationDialog(onResult:(Boolean)-> Unit){
        if(!signInNotificationDialog.isShowing) signInNotificationDialog.show()
        signInNotificationDialog.findViewById<Button>(R.id.btn_tbot_1).setOnClickListener {
            signInNotificationDialog.dismiss()
        }
        signInNotificationDialog.findViewById<Button>(R.id.btn_tbot_2).setOnClickListener {
            signInNotificationDialog.dismiss()
            showLoginDialog{ state, token ->
                when(state){
                    // 로그인 성공
                    "success" -> { onResult(true) }
                    // 로그인이 다른 곳에서 되어있는 경우
                    "disconnect"-> showSessionDialog(token!!) { result-> onResult(result) }
                }
            }
        }
    }

    // 핀 코드를 생성해야하는 경우 생성되는 다이얼로그
    fun showCreatePinCodeNotification(result:(Boolean) -> Unit){
        pinCodeNotificationDialog.findViewById<Button>(R.id.btn_tbot_1)
            .setOnClickListener {
                pinCodeNotificationDialog.dismiss()
            }
        pinCodeNotificationDialog.findViewById<Button>(R.id.btn_tbot_2)
            .setOnClickListener {
                pinCodeNotificationDialog.dismiss()
                // 핀 코드를 생성
                val pinCodeDialog = CreatePinCodeDialog{ result-> result(result)}
                pinCodeDialog.show(supportFragmentManager, "create_pin_code_dialog")
            }
        if (!pinCodeNotificationDialog.isShowing) pinCodeNotificationDialog.show()
    }

    fun showAuthPinCodeDialog(result: (Boolean)->Unit){
        val pinCodeDialog = AuthPinCodeDialog { result(it) }
        pinCodeDialog.show(supportFragmentManager, "auth_pin_code_dialog")
    }

    companion object{
        private lateinit var bottomNavigationView: BottomNavigationView

    }

}