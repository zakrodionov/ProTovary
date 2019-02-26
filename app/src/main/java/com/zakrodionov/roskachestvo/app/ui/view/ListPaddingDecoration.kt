package com.zakrodionov.roskachestvo.app.ui.view

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class ListPaddingDecoration(@NonNull context: Context) : RecyclerView.ItemDecoration() {
    private val mPadding: Int

    init {
        val metrics = context.resources.displayMetrics
        mPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, PADDING_IN_DIPS, metrics).toInt()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val itemPosition = parent.getChildAdapterPosition(view)
        if (itemPosition == RecyclerView.NO_POSITION) {
            return
        }

        val adapter = parent.adapter
        if (adapter != null && itemPosition < adapter.itemCount - 1) {
            outRect.top = mPadding
            outRect.left = mPadding
            outRect.right = mPadding
        }

        if (adapter != null && itemPosition == adapter.itemCount - 1) {
            outRect.top = mPadding
            outRect.bottom = mPadding
            outRect.left = mPadding
            outRect.right = mPadding
        }
    }

    companion object {
        private const val PADDING_IN_DIPS = 10.0F
    }

}