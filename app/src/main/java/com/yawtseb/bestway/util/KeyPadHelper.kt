package com.yawtseb.bestway.util

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.yawtseb.bestway.R


class KeyPadHelper(private val activity: Activity) {

    private var eb: Typeface? = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        activity.resources.getFont(R.font.nanumsquare_eb)
    } else {
        ResourcesCompat.getFont(activity, R.font.nanumsquare_eb)
    }
    private var r: Typeface? = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        activity.resources.getFont(R.font.nanumsquare_r)
    } else {
        ResourcesCompat.getFont(activity, R.font.nanumsquare_r)
    }
    private var l: Typeface? = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        activity.resources.getFont(R.font.nanumsquare_l)
    } else {
        ResourcesCompat.getFont(activity, R.font.nanumsquare_l)
    }

    fun textViewEBtoR(textView: TextView, length: Int) {
        if (length > 0) {
            textView.typeface = eb
        } else {
            textView.typeface = r
        }
    }

    fun radioButtonEB(radioButton: RadioButton) {
        radioButton.typeface = eb
    }

    fun radioButtonR(radioButton: RadioButton) {
        radioButton.typeface = r
    }

    fun textBackgroundTintChanged(textView: TextView, color: ColorStateList){
        textView.backgroundTintList = color
    }

    fun textColorChanged(textView: TextView, color: ColorStateList){
        textView.setTextColor(color)
    }

    fun editTextEBtoR(editText: EditText, length: Int) {
        if (length > 0) { editText.typeface = eb }
        else { editText.typeface = r }
    }

    fun goneKeyPad() {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (activity.currentFocus != null) imm.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
    }
}