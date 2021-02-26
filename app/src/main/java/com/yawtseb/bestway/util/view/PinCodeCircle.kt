package com.yawtseb.bestway.util.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.ImageView
import android.widget.LinearLayout

class PinCodeCircle: LinearLayout {

    private var mContext: Context? = null
    private var mCircle: Int = 0

    private var circleList: MutableList<ImageView> = mutableListOf()

    private val padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, resources.displayMetrics)

    constructor(context: Context) : super(context) {
        mContext = context
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mContext = context
    }

    fun createCircleList(count: Int, circle: Int) {
        this.removeAllViews()
        mCircle = circle
        for (i in 0 until count) {
            circleList.add(ImageView(mContext).apply { setPadding(padding.toInt(), 0, padding.toInt(), 0) })
            this.addView(circleList[i])
        }

        for (i in circleList.indices) {
            circleList[i].setImageResource(mCircle)
            circleList[i].isEnabled = false
        }
    }

    fun updateCircle(position: Int) {
        for (i in circleList.indices) {
            circleList[i].setImageResource(mCircle)
            circleList[i].isEnabled = i < position
        }

    }
}