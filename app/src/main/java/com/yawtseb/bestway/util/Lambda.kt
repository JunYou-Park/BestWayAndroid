package com.yawtseb.bestway.util

import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.regex.Pattern

class Lambda {
    companion object{

        fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
            this.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    afterTextChanged.invoke(s.toString())
                }
            })
        }

        fun EditText.commaTextWatcher(limitAmount: BigDecimal, commaTextWatcher: (String) -> Unit) {
            val decimalFormat = DecimalFormat("#,###")
            this.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    var commaText = ""
                    if (s.toString().isNotEmpty()) {
                        if (s.toString().replace("[^0-9]".toRegex(), "").toBigDecimal() > Int.MAX_VALUE.toBigDecimal()) {
                            commaText = "0"
                        } else {
                            if (s.toString().replace("[^0-9]".toRegex(), "").toBigDecimal() > limitAmount) {
                                commaText = decimalFormat.format(limitAmount.toString().toDouble())
                            } else {
                                commaText = decimalFormat.format(s.toString().replace("[^0-9]".toRegex(), "").toDouble())
                            }
                        }
                    }
                    commaTextWatcher.invoke(commaText)
                }
            })
        }

        fun EditText.addHyphenTextWatcher(hyphenFormatListener: (String) -> Unit) {
            var isFormatting = false
            var deletingHyphen = false
            var hyphenStart = 0
            var deletingBackward = false
            this.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(text: Editable) {

                    if (isFormatting) return
                    isFormatting = true
                    if (deletingHyphen && hyphenStart > 0) {
                        if (deletingBackward) {
                            if (hyphenStart - 1 < text.length) {
                                text.delete(hyphenStart - 1, hyphenStart)
                            }
                        } else if (hyphenStart < text.length) {
                            text.delete(hyphenStart, hyphenStart + 1)
                        }
                    }
                    if (text.length == 3 || text.length == 6) {
                        text.append('-')
                    }
                    isFormatting = false
                }

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                    if (isFormatting) return
                    val selStart = Selection.getSelectionStart(s)
                    val selEnd = Selection.getSelectionEnd(s)
                    if (s.length > 1 && count == 1 && after == 0 && s[start] == '-' && selStart == selEnd) {
                        deletingHyphen = true
                        hyphenStart = start
                        deletingBackward = selStart == start + 1
                    } else {
                        deletingHyphen = false
                    }
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    hyphenFormatListener.invoke(charSequence.toString())
                }
            })
        }

        fun String.isValid(pattern: String): Boolean = Pattern.matches(pattern, this)
    }


}