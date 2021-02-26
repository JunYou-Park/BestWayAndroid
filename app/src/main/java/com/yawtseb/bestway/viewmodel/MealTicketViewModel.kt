package com.yawtseb.bestway.viewmodel

import androidx.lifecycle.MutableLiveData
import com.yawtseb.bestway.base.BaseViewModel
import com.yawtseb.bestway.model.TicketVo
import java.text.DecimalFormat

class MealTicketViewModel: BaseViewModel() {

    private var mTicketVo = TicketVo("0","0","0","0")
    val isBuyTicket = MutableLiveData<Boolean>(false)
    val mTotalCostText = MutableLiveData<String> ("0")

    fun setTicket(ticketVo: TicketVo){
        mTicketVo = ticketVo
        val ticket5000 = mTicketVo.ticket5000.toInt()
        val ticket4000 = mTicketVo.ticket4000.toInt()
        val ticket3500 = mTicketVo.ticket3500.toInt()
        val ticket2000 = mTicketVo.ticket2000.toInt()

        val totalCost = (ticket5000*5000) + (ticket4000*4000) + (ticket3500*3500) + (ticket2000*2000)
        val totalCostText = DecimalFormat("#,###").format(totalCost)
        mTotalCostText.postValue(totalCostText)
        isBuyTicket.postValue(totalCost!=0)
    }


}