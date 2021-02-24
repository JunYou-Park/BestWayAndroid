package com.yawtseb.bestway.base

import android.app.Dialog
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.yawtseb.bestway.R
import com.yawtseb.bestway.ui.act.MainAct
import com.yawtseb.bestway.ui.dialog.pincode.CreatePinCodeDialog
import com.yawtseb.bestway.util.KeyPadHelper
import com.yawtseb.bestway.util.Lambda.Companion.addHyphenTextWatcher
import com.yawtseb.bestway.util.Lambda.Companion.afterTextChanged
import com.yawtseb.bestway.util.Lambda.Companion.commaTextWatcher
import com.yawtseb.bestway.util.SessionHelper
import com.yawtseb.bestway.util.ShowMsg

open class BaseFrag : Fragment() {

    val showMsg: ShowMsg by lazy { ShowMsg(requireActivity()) }
    private val keypadHelper: KeyPadHelper by lazy { KeyPadHelper(requireActivity()) }
    val sessionHelper: SessionHelper by lazy { SessionHelper.getInstance() }

    fun showMsg(msg: String) {
        showMsg.showShortToastMsg(msg)
    }

    private fun fontChanged(editText: EditText) = keypadHelper.editTextEBtoR(editText, editText.text.length)
    fun goneKeyPad() = keypadHelper.goneKeyPad()

    fun EditText.basicLimitTextWatcher(limit: Int, basicLimitTextWatcher: (String) -> Unit) {
        this.afterTextChanged {
            fontChanged(this)
            if (it.length > limit) {
                this.setText(it.subSequence(0, limit).toString())
                this.setSelection(limit)
            } else basicLimitTextWatcher.invoke(it)
        }
    }

    fun EditText.basicTextWatcher(basicTextWatcher: (String) -> Unit) {
        this.afterTextChanged {
            fontChanged(this)
            basicTextWatcher.invoke(it)
        }
    }

    fun EditText.basicCommaTextWatcher(limitAmount: Int, basicCommaTextWatcher: (String) -> Unit) {
        this.commaTextWatcher(limitAmount.toBigDecimal()) {
            fontChanged(this)
            basicCommaTextWatcher.invoke(it)
        }
    }

    fun EditText.basicHyphenTextWatcher(basicHyphenTextWatcher: (String) -> Unit) {
        this.addHyphenTextWatcher {
            fontChanged(this)
            basicHyphenTextWatcher.invoke(it)
        }
    }



}