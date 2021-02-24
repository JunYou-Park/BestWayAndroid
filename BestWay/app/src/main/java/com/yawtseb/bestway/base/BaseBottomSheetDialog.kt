package com.yawtseb.bestway.base

import android.widget.EditText
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yawtseb.bestway.listener.ItemClickListener
import com.yawtseb.bestway.util.KeyPadHelper
import com.yawtseb.bestway.util.Lambda.Companion.afterTextChanged
import com.yawtseb.bestway.util.ShowMsg

open class BaseBottomSheetDialog: BottomSheetDialogFragment() {

    val showMsg: ShowMsg by lazy { ShowMsg(requireActivity()) }
    private val keyPad: KeyPadHelper by lazy { KeyPadHelper(requireActivity()) }

    private var itemClickListener: ItemClickListener? = null

    private fun fontChanged(editText: EditText) =
            keyPad.editTextEBtoR(editText, editText.text.length)

    fun EditText.basicTextWatcher(basicTextWatcher: (String) -> Unit) {
        this.afterTextChanged {
            fontChanged(this)
            basicTextWatcher.invoke(it)
        }
    }
}