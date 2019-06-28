package com.cardemory.common.ui

abstract class BaseSelectableAdapter<T : Any, H : BaseHolder<T>>(
    private val onItemSelected: (T, selected: Boolean) -> Unit
) : BaseAdapter<T, H>() {

    protected val selectedItemsPositions = mutableSetOf<Int>()

    var selectionMode = false
        private set

    open fun setSelectionModeEnabled(enabled: Boolean) {
        selectionMode = enabled
        selectedItemsPositions.clear()
        notifyItemRangeChanged(0, itemCount, Unit)
    }

    open fun selectItem(position: Int) {
        selectedItemsPositions.add(position)
        notifyItemChanged(position, Unit)
        onItemSelected(getItem(position), true)
    }

    open fun unSelectItem(position: Int) {
        selectedItemsPositions.remove(position)
        notifyItemChanged(position, Unit)
        onItemSelected(getItem(position), false)
    }

    fun isItemPositionSelected(position: Int) =
        selectedItemsPositions.contains(position)

    fun getSelectedItemsCount() = selectedItemsPositions.size

    open fun getSelectedItems(): List<T> {
        val adapterItems = getItems()
        return selectedItemsPositions.map(adapterItems::get)
    }

    override fun onBindViewHolder(
        holder: H,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        holder.bind(getItem(position), position)
    }
}