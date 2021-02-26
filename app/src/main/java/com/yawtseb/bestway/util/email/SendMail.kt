package com.airetefacruo.myapplication.util.email

import com.yawtseb.bestway.util.ShowMsg.Companion.showLog


class SendMail {
    fun sendSecurityCode(subject: String, sendTo: String, url: String, local: String){
        try{
            showLog("SendMail Suc: $subject, sendTo: $sendTo")
            val gMailSender = GMailSender()
            gMailSender.sendAuthEmail(subject, sendTo, url, local)
        }
        catch(e: Exception){
            showLog("SendMail Err: $e")
        }
    }
}