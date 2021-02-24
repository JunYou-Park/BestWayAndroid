package com.yawtseb.bestway.ui.frag.account

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.airetefacruo.myapplication.util.network.Status
import com.yawtseb.bestway.R
import com.yawtseb.bestway.adapter.AccountMenuAdapter
import com.yawtseb.bestway.base.BaseFrag
import com.yawtseb.bestway.databinding.FragmentAccountBinding
import com.yawtseb.bestway.ui.act.MainAct
import com.yawtseb.bestway.util.ShowMsg.Companion.showLog
import com.yawtseb.bestway.viewmodel.AccountViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class AccountFrag : BaseFrag() {

    private val binding: FragmentAccountBinding by lazy { FragmentAccountBinding.inflate(layoutInflater) }
    private lateinit var accountMenuAdapter: AccountMenuAdapter


    private val logoutDialog:Dialog by lazy { showMsg.showOneTitleTwoButtonDialog(getString(R.string.logout_question), getString(R.string.cancel), getString(R.string.confirm)) }
    private val withdrawalDialog:Dialog by lazy { showMsg.showOneTitleTwoButtonDialog(getString(R.string.withdrawal_question), getString(R.string.cancel), getString(R.string.confirm)) }

    private val accountViewModel: AccountViewModel by viewModel()
    private val navController: NavController by lazy { Navigation.findNavController(requireView()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loadingView.create()

        setAdapter()

        setObserver()

        setHeadTitle()
    }

    private fun setAdapter(){
        val menuList = ArrayList(requireContext().resources.getStringArray(R.array.account_menu_list).toList())
        val accountManageList = ArrayList(requireContext().resources.getStringArray(R.array.account_manage_menu_list).toList())

        val accountMenuItemList = arrayListOf<AccountMenuAdapter.AccountMenuItem>()

        // 메뉴 리스트를 넣는다
        for(i in menuList.indices){
            accountMenuItemList.add(AccountMenuAdapter.AccountMenuItem(type = 0, text = menuList[i]))

            // 메뉴 리스트 중 계정관리가 있는 경우
            if(menuList[i] == getString(R.string.modify_account_manage)){
                accountMenuItemList[i].inVisibleChildren = arrayListOf()
                // 계정관리 안에 자식 리스트를 넣는다.
                for(text in accountManageList){
                    accountMenuItemList[i].inVisibleChildren?.add(AccountMenuAdapter.AccountMenuItem(type = 1, text = text))
                }
            }
        }

        accountMenuAdapter = AccountMenuAdapter(onClick = { item ->
            showMsg(item)
            // 앱 정보 보기는 로그인할 필요 없음
            if(item == getString(R.string.app_info)){
                navController.navigate(R.id.action_accountFrag_to_appInfoFrag)
            }
            else{
                if(sessionHelper.isLogin()){
                    when(item){
                        // 장바구니 보기
                        getString(R.string.view_cart) -> {
                        }
                        // 구매내역 확인
                        getString(R.string.check_purchase_list) -> {

                        }

                        getString(R.string.change_account_info)->{

                        }
                        getString(R.string.logout)->{
                            showLogoutDialog()
                        }
                        getString(R.string.withdrawal)->{
                            showWithdrawalDialog()
                        }
                    }
                }
                else{
                    (requireActivity() as MainAct).showSignInNotificationDialog{ result->
                        if(result) setHeadTitle()
                        else showMsg(getString(R.string.fail_update_session))
                    }
                }
            }


        })
        accountMenuAdapter.update(accountMenuItemList)
        binding.rvAccountMenu.adapter = accountMenuAdapter
    }

    private fun setHeadTitle(){
        if(sessionHelper.isLogin())binding.tvAccountHeaderTitle.text = getString(R.string.login_state)
        else binding.tvAccountHeaderTitle.text = getString(R.string.need_sign_in_exist)
    }

    private fun setObserver(){
        accountViewModel.logout.observe(viewLifecycleOwner, Observer{
            val resource = it ?: return@Observer

            binding.loadingView.isVisible()

            showLog("logout: ${resource.data}")

            when(resource.status){
                Status.SUCCESS -> {
                    showMsg(resource.data?.msg.toString())
                    // 토큰 리셋
                    sessionHelper.resetToken()
                }
                Status.ERROR ->{
                    showMsg(resource.message.toString())
                }
                Status.ERROR_INT -> {
                    showMsg(getString(resource.intMsg!!))
                }
                else -> return@Observer
            }

            accountViewModel.logout.postValue(null)
        })

        accountViewModel.withdrawal.observe(viewLifecycleOwner, Observer{
            val resource = it ?: return@Observer

            binding.loadingView.isVisible()

            when(resource.status){
                Status.SUCCESS -> {
                    showMsg(resource.data?.msg.toString())
                    // 토큰 리셋
                    sessionHelper.resetToken()
                }
                Status.ERROR ->{
                    showMsg(resource.message.toString())
                }
                Status.ERROR_INT -> {
                    showMsg(getString(resource.intMsg!!))
                }
                else -> return@Observer
            }

            accountViewModel.withdrawal.postValue(null)
        })
    }

    private fun showLogoutDialog(){
        logoutDialog.findViewById<Button>(R.id.btn_tbot_1).setOnClickListener {
            logoutDialog.dismiss()
        }
        logoutDialog.findViewById<Button>(R.id.btn_tbot_2).setOnClickListener {
            accountViewModel.logout(sessionHelper.tokenVo?.accessToken.toString())
            logoutDialog.dismiss()
        }

        if(!logoutDialog.isShowing) logoutDialog.show()
    }

    private fun showWithdrawalDialog(){
        withdrawalDialog.findViewById<Button>(R.id.btn_tbot_1).setOnClickListener {
            withdrawalDialog.dismiss()
        }
        withdrawalDialog.findViewById<Button>(R.id.btn_tbot_2).setOnClickListener {
            accountViewModel.withdrawal(sessionHelper.tokenVo?.accessToken.toString())
            withdrawalDialog.dismiss()
        }

        if(!logoutDialog.isShowing) withdrawalDialog.show()
    }


}