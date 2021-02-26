package com.yawtseb.bestway.util

import com.yawtseb.bestway.model.TicketVo
import com.yawtseb.bestway.model.TokenVo

class SessionHelper {
    companion object{
        @Volatile
        private var instance: SessionHelper? = null

        @Synchronized
        fun getInstance() = instance ?: SessionHelper().also { instance = it }
    }

    var tokenVo: TokenVo? = null
    var ticketVo: TicketVo? = null

    fun setToken(tokenVo: TokenVo?){
        this.tokenVo = tokenVo
    }

    fun resetToken(){
        this.tokenVo = null
    }

    fun isLogin() = tokenVo != null

    fun setTicket(ticketVo: TicketVo){
        this.ticketVo = ticketVo
    }

    fun hasTicket() = ticketVo != null

}