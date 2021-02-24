package com.yawtseb.bestway.util.deco

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridDecoration(private val margin: Int = 0, private val columns:Int): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if(position%2==0){
            outRect.right = margin / 3
            outRect.left = margin
        }
        else {
            outRect.right = margin
            outRect.left = margin / 3
        }

        if(position == 0 || position == 1){
            outRect.top = margin
            outRect.bottom = margin / 3
        }
        else if(position == columns - 2 || position == columns - 1){
            outRect.top = margin / 3
            outRect.bottom = margin
        }
        else {
            outRect.top = margin / 3
            outRect.bottom = margin / 3
        }

    }
}