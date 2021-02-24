package com.yawtseb.bestway.ui.frag.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.airetefacruo.myapplication.util.network.Status
import com.bumptech.glide.Glide
import com.yawtseb.bestway.R
import com.yawtseb.bestway.base.BaseFrag
import com.yawtseb.bestway.databinding.FragmentDietMenuBinding
import com.yawtseb.bestway.model.MenuVo
import com.yawtseb.bestway.model.OrderMenuVo
import com.yawtseb.bestway.model.TicketVo
import com.yawtseb.bestway.ui.act.MainAct
import com.yawtseb.bestway.ui.dialog.ImageDialog
import com.yawtseb.bestway.util.ConstData.IMAGE_URL
import com.yawtseb.bestway.util.ConstData.MODEL_KEY
import com.yawtseb.bestway.util.ConstData.ORDER_KEY
import com.yawtseb.bestway.viewmodel.DietViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.DecimalFormat

class DietMenuFrag : BaseFrag() {

    private val binding: FragmentDietMenuBinding by lazy { FragmentDietMenuBinding.inflate(layoutInflater) }
    private val dietViewModel: DietViewModel by viewModel()
    private val navController: NavController by lazy { Navigation.findNavController(requireView()) }

    private lateinit var menu: MenuVo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        menu = requireArguments().getParcelable(MODEL_KEY)!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBinding()

        setOnClickListener()

        setObserver()

    }

    private fun initBinding(){
        binding.tvDietMenuName.text = menu.menu_name
        binding.tvDietMenuSummary.text = menu.menu_summary

        val price = "${DecimalFormat("#,###").format(menu.menu_price.toInt())} 원"
        binding.tvDietMenuPrice.text = price

        Glide.with(requireContext()).load("$IMAGE_URL${menu.menu_thumb}").into(binding.ivDietMenuThumb)
    }

    private fun setOnClickListener(){
        binding.btnDietMenuThumbDetail.setOnClickListener {
            val dialog: DialogFragment = ImageDialog.newInstance("$IMAGE_URL${menu.menu_thumb}")
            dialog.show(requireActivity().supportFragmentManager, "image_dialog")
        }

        binding.btnDietMenuBuy.setOnClickListener {
            if(sessionHelper.isLogin()){
                compareTicket()
            }
            else{
                (requireActivity() as MainAct).showSignInNotificationDialog { result->
                    if(result) getTicket()
                    else showMsg(getString(R.string.fail_update_session))
                }
            }
        }
    }

    private fun getTicket(){
        dietViewModel.getUserTicket(sessionHelper.tokenVo?.accessToken.toString())
    }

    private fun setObserver(){
        dietViewModel.getUserTicket.observe(viewLifecycleOwner, Observer {
            val resource = it ?: return@Observer

            when(resource.status){
                Status.SUCCESS ->{
                    if(resource.data != null){
                        val ticket = resource.data
                        sessionHelper.setTicket(TicketVo(ticket5000 = ticket.ticket5000, ticket4000 = ticket.ticket4000, ticket3500 = ticket.ticket3500, ticket2000 = ticket.ticket2000))
                        compareTicket()
                    }
                }
                Status.ERROR_INT ->{
                    showMsg(getString(R.string.error_get_ticket))
                }
                Status.ERROR ->{
                    showMsg(getString(R.string.error_get_ticket))
                }
                else -> return@Observer
            }
        })
        dietViewModel.getUserTicket.postValue(null)
    }

    private fun compareTicket(){
        val ticket = sessionHelper.ticketVo!!
        when(menu.menu_price){
            "5000" -> {
                if(ticket.ticket5000.toInt() > 0) {
                    directQrFrag()
                }
                else showMsg("${binding.tvDietMenuPrice.text.toString()} 식권이 부족합니다.")
            }
            "4000" -> {
                if(ticket.ticket4000.toInt() > 0){
                    directQrFrag()
                }
                else showMsg("${binding.tvDietMenuPrice.text.toString()} 식권이 부족합니다.")
            }
            "3500" -> {
                if(ticket.ticket3500.toInt() > 0){
                    directQrFrag()
                }
                else showMsg("${binding.tvDietMenuPrice.text.toString()} 식권이 부족합니다.")
            }
            "2000" -> {
                if(ticket.ticket2000.toInt() > 0){
                    directQrFrag()
                }
                else showMsg("${binding.tvDietMenuPrice.text.toString()} 식권이 부족합니다.")
            }
        }
    }

    private fun directQrFrag(){
        val orderMenuVo = OrderMenuVo(menu_id = menu.menu_id, menu_amount = 1)
        val arg = Bundle()
        arg.putBoolean(ORDER_KEY, true)
        arg.putParcelable(MODEL_KEY, orderMenuVo)
        navController.navigate(R.id.action_dietMenuFrag_to_QRFrag, arg)
    }

}