package com.volynkun.cft_focus_start.extensions

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class ItemOffsetDecoration(
    private val context: Context,
    private val leftOffset: Int,
    private val rightOffset: Int,
    private val topOffset: Int,
    private val bottomOffset: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            left = leftOffset.fromDPToPixels(context)
            right = rightOffset.fromDPToPixels(context)
            top = topOffset.fromDPToPixels(context)
            bottom = bottomOffset.fromDPToPixels(context)
        }
    }

}