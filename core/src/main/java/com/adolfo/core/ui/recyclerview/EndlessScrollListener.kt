package com.adolfo.core.ui.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EndlessScrollListener(
    private val endlessScroll: () -> Unit
) : RecyclerView.OnScrollListener() {

    private val minPositionToEnd = 2
    private var previousItemCount = 0
    private var isLoading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE) {
            return
        }

        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val totalItems = layoutManager.itemCount

        if (isLoading) {
            if (totalItems > previousItemCount) {
                isLoading = false
                previousItemCount = totalItems
            }
        } else {
            if (totalItems ==
                layoutManager.findLastCompletelyVisibleItemPosition() + minPositionToEnd
            ) {
                isLoading = true
                endlessScroll()
            }
        }
    }
}
