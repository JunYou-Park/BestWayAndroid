package com.yawtseb.bestway.ui.frag.home

import android.graphics.Paint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.airetefacruo.myapplication.util.network.Status
import com.yawtseb.bestway.R
import com.yawtseb.bestway.adapter.HomeTodayDietAdapter
import com.yawtseb.bestway.base.BaseFrag
import com.yawtseb.bestway.databinding.FragmentHomeBinding
import com.yawtseb.bestway.listener.ItemClickListener
import com.yawtseb.bestway.model.MenuVo
import com.yawtseb.bestway.model.TicketVo
import com.yawtseb.bestway.ui.act.MainAct
import com.yawtseb.bestway.util.ConstData.MODEL_KEY
import com.yawtseb.bestway.util.Resolution.Companion.getDimension
import com.yawtseb.bestway.util.deco.GridDecoration
import com.yawtseb.bestway.viewmodel.DietViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFrag : BaseFrag() {

    private val binding: FragmentHomeBinding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController

    private val dietViewModel: DietViewModel by viewModel()
    private val dietAdapter = HomeTodayDietAdapter()

    override fun onResume() {
        super.onResume()
        getTicket()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.refreshHomeParent.setOnRefreshListener {
            getDiet()
            getTicket()
            binding.refreshHomeParent.isRefreshing = false
        }

        getDiet()

        setObserver()

        setOnClickListener()

    }

    private fun setAdapter() {
        while(binding.includeHomeTodayDiet.rvHomeTodayDiet.itemDecorationCount>0){
            binding.includeHomeTodayDiet.rvHomeTodayDiet.removeItemDecorationAt(0)
        }

        binding.includeHomeTodayDiet.rvHomeTodayDiet.apply {
            adapter = dietAdapter
            addItemDecoration(GridDecoration(margin = requireContext().getDimension(24f), columns = dietAdapter.itemCount))
        }
        dietAdapter.setListener(object : ItemClickListener{
            override fun onItemClick(model: Any, position: Int) {
                val menu = model as MenuVo
                val arg = Bundle()
                arg.putParcelable(MODEL_KEY, menu)
                navController.navigate(R.id.action_homeFrag_to_dietMenuFrag, arg)
            }
        })
    }

    private fun setOnClickListener(){
        binding.lineHomeMealTicketContainer.setOnClickListener {
            (requireActivity() as MainAct).showLoginDialog { state ->
                when(state) {
                    // 로그인 성공
                    "success" -> getTicket()
                }
            }
        }
    }

    private fun getDiet(){
        dietViewModel.getToDayDiet()
    }

    private fun getTicket(){
        binding.lineHomeMealTicketContainer.isClickable = !sessionHelper.isLogin()
        if(sessionHelper.isLogin()){
            dietViewModel.getUserTicket(sessionHelper.tokenVo?.userAccessToken.toString()) }
        else {
            binding.tvHomeMealTicketAmount.text = getString(R.string.go_to_sign_in)
            binding.tvHomeMealTicketAmount.textSize = 16f
            binding.tvHomeMealTicketAmount.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            binding.tvHomeMealTicketAmount.gravity = Gravity.CENTER
        }
    }

    private fun setObserver(){
        dietViewModel.toDayMenuLiveData.observe(viewLifecycleOwner, Observer {
            val resource = it ?: return@Observer

            when(resource.status){
                Status.SUCCESS -> {
                    binding.includeHomeTodayDiet.pbHomeTodayDiet.isVisible = false
                    binding.includeHomeTodayDiet.tvHomeTodayDietNone.isVisible = resource.data.isNullOrEmpty()
                    dietAdapter.submitList(resource.data)
                    setAdapter()
                }
                Status.LOADING->{
                    binding.includeHomeTodayDiet.pbHomeTodayDiet.isVisible = true

                }
                Status.ERROR -> {
                    binding.includeHomeTodayDiet.pbHomeTodayDiet.isVisible = false
                    showMsg(resource.message.toString())

                }
                Status.ERROR_INT -> {
                    binding.includeHomeTodayDiet.pbHomeTodayDiet.isVisible = false
                    showMsg(getString(resource.intMsg!!))

                }
                else -> return@Observer
            }
        })

        dietViewModel.getUserTicket.observe(viewLifecycleOwner, Observer {
            val resource = it ?: return@Observer

            when(resource.status){
                Status.SUCCESS ->{
                    if(resource.data != null){
                        // 텍스트 크기 및 위치 조정
                        sessionHelper.setTicket(TicketVo(ticket5000 = resource.data.ticket5000, ticket4000 = resource.data.ticket4000, ticket3500 = resource.data.ticket3500, ticket2000 = resource.data.ticket2000))
                        val ticketAmount = "${getString(R.string.meal_ticket)} 5000${getString(R.string.k_won)}: ${resource.data.ticket5000}, 4000${getString(R.string.k_won)}: ${resource.data.ticket4000}, 3500${getString(R.string.k_won)}: ${resource.data.ticket3500}, 2000${getString(R.string.k_won)}: ${resource.data.ticket2000}"
                        binding.tvHomeMealTicketAmount.text = ticketAmount
                        binding.tvHomeMealTicketAmount.textSize = 14f
                        binding.tvHomeMealTicketAmount.paintFlags = Paint.LINEAR_TEXT_FLAG
                        binding.tvHomeMealTicketAmount.gravity = Gravity.START or Gravity.CENTER_VERTICAL
                    }
                }
                Status.ERROR_INT ->{
                    binding.tvHomeMealTicketAmount.text = getString(R.string.error_get_ticket)
                }
                Status.ERROR ->{
                    binding.tvHomeMealTicketAmount.text = getString(R.string.error_get_ticket)
                }
                else -> return@Observer
            }
        })
    }

}