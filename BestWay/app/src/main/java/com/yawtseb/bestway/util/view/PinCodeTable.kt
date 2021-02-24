package com.yawtseb.bestway.util.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.yawtseb.bestway.R
import com.yawtseb.bestway.listener.PinCodeClickListener


class PinCodeTable(context: Context?, attrs: AttributeSet?) : TableLayout(context, attrs), View.OnClickListener {

    private lateinit var pinCodeClickListener: PinCodeClickListener

    fun setPinCodeClickListener(listener: PinCodeClickListener){
        pinCodeClickListener = listener
    }


    private val temp = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, 18f, resources.displayMetrics
    )

    fun createPad(){
        this.removeAllViews()

        val padList = arrayListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
        padList.shuffle()
        padList.add(9, R.drawable.ic_baseline_refresh_24)
        padList.add(R.drawable.ic_baseline_subdirectory_arrow_left_24)

        var index = 0;
        for(i in 0 until 4){
            val tableRow = TableRow(context)

            tableRow.layoutParams = TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            val trLayoutParams = TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            trLayoutParams.weight = 1f

            for(j in 0 until 3){
                // 마지막 키패드 라인
                if(i == 3){
                    when (index) {
                        9 -> { // 초기화 버튼
                            val imageButton = getImageButton(padList[index++])
                            imageButton.tag = "reset"
                            imageButton.layoutParams = trLayoutParams
                            tableRow.addView(imageButton)
                        }
                        padList.size - 1 -> { // 삭제 버튼
                            val imageButton = getImageButton(padList[index++])
                            imageButton.tag = "del"
                            imageButton.layoutParams = trLayoutParams
                            tableRow.addView(imageButton)
                        }
                        else -> {
                            val button = getButton(padList[index++].toString())
                            button.layoutParams = trLayoutParams
                            tableRow.addView(button)
                        }
                    }
                }
                else{
                    val button = getButton(padList[index++].toString())
                    button.layoutParams = trLayoutParams
                    tableRow.addView(button)
                }
            }
            this.addView(tableRow)

        }
    }

    private fun getImageButton(inputResource: Int): ImageButton{
        val imageButton = ImageButton(context)
        imageButton.setOnClickListener(this)

        imageButton.setImageResource(inputResource)
        imageButton.setBackgroundResource(R.drawable.selector_pin_code_pad)
        imageButton.setPadding(temp.toInt(), temp.toInt(), temp.toInt(), temp.toInt())
        imageButton.imageTintList = ContextCompat.getColorStateList(context, R.color.black)
        return imageButton
    }

    private fun getButton(inputString: String): Button{
        val button = Button(context)
        button.setOnClickListener(this)

        button.text = inputString
        button.tag = inputString

        button.typeface = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) { context.resources.getFont(R.font.nanumsquare_eb) }
        else { ResourcesCompat.getFont(context, R.font.nanumsquare_eb) }
        button.textSize = 18f

        button.setTextColor(ContextCompat.getColor(context, R.color.black))
        button.setPadding(temp.toInt(), temp.toInt(), temp.toInt(), temp.toInt())
        button.setBackgroundResource(R.drawable.selector_pin_code_pad)
        button.gravity = Gravity.CENTER;

        return  button
    }

    override fun onClick(v: View?) {
        if (v != null) {
            pinCodeClickListener.onPinCodeClick(v.tag.toString())
        }
    }
}