package com.yawtseb.bestway.util.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.yawtseb.bestway.R

class LoadingView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    fun create(){
        this.removeAllViews()

        val constraintSet = ConstraintSet()
        val view: View = View(context)
        val progressBar:ProgressBar = ProgressBar(context, null, android.R.attr.progressBarStyle)

        this.addView(view)
        this.addView(progressBar)

        this.isVisible = false
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.black_trans_60))

        // 아이디를 생성해줘야 뷰를 붙일 수 있다..
        view.id = View.generateViewId()
        progressBar.id = View.generateViewId()

        constraintSet.clear(view.id)
        constraintSet.connect(view.id, ConstraintSet.START, this.id, ConstraintSet.START, 0)
        constraintSet.connect(view.id, ConstraintSet.TOP, this.id, ConstraintSet.TOP, 0)
        constraintSet.connect(view.id, ConstraintSet.END, this.id, ConstraintSet.END, 0)
        constraintSet.connect(view.id, ConstraintSet.BOTTOM, this.id, ConstraintSet.BOTTOM, 0)
        constraintSet.constrainHeight(view.id, ConstraintSet.MATCH_CONSTRAINT)
        constraintSet.constrainWidth(view.id, ConstraintSet.MATCH_CONSTRAINT)

        constraintSet.clear(progressBar.id)
        constraintSet.connect(progressBar.id, ConstraintSet.START, this.id, ConstraintSet.START, 0)
        constraintSet.connect(progressBar.id, ConstraintSet.TOP, this.id, ConstraintSet.TOP, 0)
        constraintSet.connect(progressBar.id, ConstraintSet.END, this.id, ConstraintSet.END, 0)
        constraintSet.connect(
            progressBar.id,
            ConstraintSet.BOTTOM,
            this.id,
            ConstraintSet.BOTTOM,
            0
        )
        constraintSet.constrainHeight(progressBar.id, ConstraintSet.WRAP_CONTENT)
        constraintSet.constrainWidth(progressBar.id, ConstraintSet.WRAP_CONTENT)
        constraintSet.applyTo(this)

    }

    fun isVisible(){
        this.isVisible = !this.isVisible
    }

    fun visible() = this.isVisible

}