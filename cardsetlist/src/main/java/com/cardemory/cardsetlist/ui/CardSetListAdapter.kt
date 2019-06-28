package com.cardemory.cardsetlist.ui

import android.view.View
import com.cardemory.carddata.entity.CardSet
import com.cardemory.cardsetlist.R
import com.cardemory.common.ui.BaseAdapter
import com.cardemory.common.ui.BaseHolder
import com.cardemory.common.util.setVisible
import kotlinx.android.synthetic.main.item_card_set.view.*

class CardSetListAdapter(
    private val onCardSetClickListener: (CardSet) -> Unit,
    private val onEditClicked: (CardSet) -> Unit,
    private val onDeleteClicked: (CardSet) -> Unit,
    private val onCardSetSelected: (CardSet, selected: Boolean) -> Unit
) : BaseAdapter<CardSet, CardSetListAdapter.CardSetHolder>() {

    private val selectedItemsPositions = mutableSetOf<Int>()

    var selectionMode = false
        private set

    fun setSelectionModeEnabled(enabled: Boolean) {
        selectionMode = enabled
        selectedItemsPositions.clear()
        notifyItemRangeChanged(0, getItems().size, Unit)
    }

    fun selectItem(position: Int) {
        selectedItemsPositions.add(position)
        notifyItemChanged(position, Unit)
        onCardSetSelected(getItem(position), true)
    }

    fun unSelectItem(position: Int) {
        selectedItemsPositions.remove(position)
        notifyItemChanged(position, Unit)
        onCardSetSelected(getItem(position), false)
    }

    fun getSelectedItemsCount() = selectedItemsPositions.size

    fun getSelectedItems(): List<CardSet> {
        val selectedItems = mutableListOf<CardSet>()
        selectedItemsPositions.forEach {
            selectedItems.add(getItems()[it])
        }
        return selectedItems.toList()
    }

    override fun layoutId(viewType: Int) = R.layout.item_card_set

    override fun getHolder(view: View, viewType: Int) = CardSetHolder(view)

    override fun onBindViewHolder(
        holder: CardSetHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        holder.bind(getItem(position), position)
    }

    inner class CardSetHolder(itemView: View) : BaseHolder<CardSet>(itemView) {

        override fun bind(uiEntity: CardSet, position: Int) {
            super.bind(uiEntity, position)
            itemView.container.setOnClickListener {
                onCardSetClicked(uiEntity)
            }
            itemView.container.setOnCreateContextMenuListener { menu, _, _ ->
                menu.add(R.string.edit).setOnMenuItemClickListener {
                    onEditClicked(uiEntity)
                    true
                }
                menu.add(R.string.delete).setOnMenuItemClickListener {
                    onDeleteClicked(uiEntity)
                    true
                }
            }
            itemView.container.isLongClickable = !selectionMode
            itemView.cardSetNameTextView.text = uiEntity.name

            itemView.cardSetCheckBox.setVisible(selectionMode)
            itemView.cardSetCheckBox.isChecked =
                selectedItemsPositions.contains(adapterPosition)
        }

        private fun onCardSetClicked(cardSet: CardSet) {
            if (selectionMode) {
                selectedItemsPositions.contains(adapterPosition).also {
                    if (it) {
                        selectedItemsPositions.remove(adapterPosition)
                    } else {
                        selectedItemsPositions.add(adapterPosition)
                    }
                    itemView.cardSetCheckBox.isChecked = !it
                    onCardSetSelected(cardSet, !it)
                }
            } else {
                onCardSetClickListener(cardSet)
            }
        }
    }
}
