package com.cardemory.common.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class BaseAdapter<T : Any, H : BaseHolder<T>> :
    RecyclerView.Adapter<H>() {

    private val itemList: MutableList<T> = arrayListOf()

    @LayoutRes
    protected abstract fun layoutId(viewType: Int): Int

    override fun getItemCount(): Int = itemList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H {
        val view = LayoutInflater.from(parent.context).inflate(layoutId(viewType), parent, false)
        return getHolder(view, viewType)
    }

    protected abstract fun getHolder(view: View, viewType: Int): H

    override fun onBindViewHolder(holder: H, position: Int) {
        val item = itemList[position]
        holder.bind(item, position)
    }

    open fun swapData(items: List<T>, withNotify: Boolean = true) {
        itemList.clear()
        itemList.addAll(items)
        if (withNotify) {
            notifyDataSetChanged()
        }
    }

    open fun removeItem(item: T) {
        val position = itemList.indexOf(item)
        itemList.removeAt(position)
        notifyItemRemoved(position)
    }

    open fun removeItems(items: List<T>) {
        items.forEach(::removeItem)
    }

    open fun getItems(): List<T> = Collections.unmodifiableList(itemList)

    fun getItem(index: Int) = itemList[index]

    fun hasEmptyList(): Boolean = itemList.isEmpty()
}