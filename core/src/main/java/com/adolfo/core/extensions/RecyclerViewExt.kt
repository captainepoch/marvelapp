package com.adolfo.core.extensions

import androidx.recyclerview.widget.RecyclerView
import com.adolfo.core.ui.recyclerview.EndlessScrollListener

fun RecyclerView.endlessScrollListener(endlessListener: () -> (Unit)) {
    this.addOnScrollListener(EndlessScrollListener(endlessListener))
}
