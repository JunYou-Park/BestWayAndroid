package com.yawtseb.bestway.util

import android.app.Activity
import android.app.Dialog
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.yawtseb.bestway.R

class ShowMsg(private val activity: Activity) {

    fun showShortToastMsg(msg: String) = Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()

    fun showOneTitleOneButtonDialog(title: String, button: String): Dialog {
        return Dialog(activity, R.style.RoundDialog).apply {
            this.setContentView(R.layout.layout_one_button_one_title_dialog)

            this.findViewById<TextView>(R.id.tv_obot_title).text = title
            this.findViewById<TextView>(R.id.btn_obot_button).text = button
        }
    }
    fun showOneTitleTwoButtonDialog(title: String, button1: String, button2: String): Dialog {
        return Dialog(activity, R.style.RoundDialog).apply {
            this.setContentView(R.layout.layout_two_button_one_title_dialog)

            this.findViewById<TextView>(R.id.tv_tbot_title).text = title
            this.findViewById<TextView>(R.id.btn_tbot_1).text = button1
            this.findViewById<TextView>(R.id.btn_tbot_2).text = button2
        }
    }
    companion object{
        fun showLog(msg:Any, tag: String?= null) {
            if(tag!=null) Log.d("확인", "$tag: $msg")
            else Log.d("확인", "$msg")
        }
    }

}