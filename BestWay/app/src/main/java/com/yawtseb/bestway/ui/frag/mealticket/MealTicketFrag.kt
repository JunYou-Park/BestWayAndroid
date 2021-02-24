package com.yawtseb.bestway.ui.frag.mealticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.airetefacruo.myapplication.util.network.Status
import com.yawtseb.bestway.R
import com.yawtseb.bestway.adapter.MealTicketAdapter
import com.yawtseb.bestway.base.BaseFrag
import com.yawtseb.bestway.databinding.FragmentMealTicketBinding
import com.yawtseb.bestway.listener.ItemClickListener
import com.yawtseb.bestway.model.TicketVo
import com.yawtseb.bestway.ui.act.MainAct
import com.yawtseb.bestway.util.ShowMsg.Companion.showLog
import com.yawtseb.bestway.viewmodel.AccountViewModel
import com.yawtseb.bestway.viewmodel.MealTicketViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MealTicketFrag : BaseFrag() {

    private val binding: FragmentMealTicketBinding by lazy { FragmentMealTicketBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController

    private val mealTicketAdapter = MealTicketAdapter()
    private val ticketPriceList = arrayListOf<String>("5000", "4000", "3500", "2000")

    private var ticketVo =  TicketVo("0","0","0","0")
    private val mealTicketViewModel: MealTicketViewModel by lazy { ViewModelProvider(requireActivity()).get(MealTicketViewModel::class.java) }
    private val accountViewModel: AccountViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        setAdapter()

        setObserver()

        setOnClickListener()
    }

    private fun setOnClickListener(){
        binding.btnMealTicketBuy.setOnClickListener {
            if(sessionHelper.isLogin()){
                // 인증 절차
                setAuthProcess()
            }
            else{
                (requireActivity() as MainAct).showSignInNotificationDialog { result->
                    if(result){
                        // 인증 절차
                        setAuthProcess()
                    }
                    else{ showMsg(getString(R.string.fail_update_session)) }
                }
            }
        }
    }

    private fun setAdapter(){
        binding.rvMealTicketList.adapter = mealTicketAdapter
        mealTicketAdapter.update(ticketPriceList)
        mealTicketAdapter.setItemClickListener(object : ItemClickListener{
            override fun onItemClick(model: Any, position: Int) {
                showLog("position: $position, amount: $model")
                when(position){
                    0-> ticketVo.ticket5000 = model.toString()
                    1-> ticketVo.ticket4000 = model.toString()
                    2-> ticketVo.ticket3500 = model.toString()
                    3-> ticketVo.ticket2000 = model.toString()
                }
                mealTicketViewModel.setTicket(ticketVo)
            }

        })
    }

    private fun setObserver(){
        mealTicketViewModel.isBuyTicket.observe(viewLifecycleOwner,{
            binding.btnMealTicketBuy.isEnabled = it
        })
        mealTicketViewModel.mTotalCostText.observe(viewLifecycleOwner,{
            val totalCostText = "$it ${getString(R.string.k_won)}"
            binding.tvMealTicketTotalCost.text = totalCostText
        })
    }

    private fun setAuthProcess(){
        // 가운데 자리수가 1이면 핀 코드가 생성되어 있는 상태이기 때문에 -10 했을때 110, 111이면 100 이상이여야한다.
        if(sessionHelper.tokenVo!!.userAuth.toInt() -10 >= 100){
            // 핀 코드 인증 다이얼로그 생성
            (requireActivity() as MainAct).showAuthPinCodeDialog{
                if(it) showMsg("핀 코드가 인증되었습니다.")
                else showMsg("핀 코드 인증에 실패하였습니다.")
            }
        }
        else{
            // 핀 코드 생성 다이얼로그 생성
            (requireActivity() as MainAct).showCreatePinCodeNotification { result ->
                // 핀 코드가 생성된 경우
                if(result) binding.btnMealTicketBuy.performClick()
            }
        }
    }

}