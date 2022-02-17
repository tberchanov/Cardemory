package com.cardemory.common.ui

import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView

abstract class BaseHolder<T : Any>(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    protected lateinit var data: T

    @CallSuper
    open fun bind(uiEntity: T, position: Int) {
        data = uiEntity
    }
}