package com.cardemory.cardsetlist.ui

import android.view.ContextMenu
import android.view.View
import com.cardemory.carddata.entity.CardSet
import com.cardemory.cardsetlist.R
import com.cardemory.common.ui.BaseHolder
import com.cardemory.common.ui.BaseSelectableAdapter
import com.cardemory.common.util.ext.setVisible
import com.cardemory.memory_label.CardSetMemoryLabelTransformer
import kotlinx.android.synthetic.main.item_card_set.view.*

class CardSetListAdapter(
    private val onCardSetClickListener: (CardSet) -> Unit,
    private val onEditClicked: (CardSet) -> Unit,
    private val onDeleteClicked: (CardSet) -> Unit,
    private val onCardSetSelected: (CardSet, selected: Boolean) -> Unit,
    private val cardSetMemoryLabelTransformer: CardSetMemoryLabelTransformer
) : BaseSelectableAdapter<CardSet, CardSetListAdapter.CardSetHolder>(onCardSetSelected) {

    override fun layoutId(viewType: Int) = R.layout.item_card_set

    override fun getHolder(view: View, viewType: Int) = CardSetHolder(view)

    inner class CardSetHolder(itemView: View) : BaseHolder<CardSet>(itemView) {

        @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
        override fun bind(cardSet: CardSet, position: Int) {
            super.bind(cardSet, position)
            itemView.container.setOnClickListener {
                onCardSetClicked(cardSet)
            }
            itemView.container.setOnCreateContextMenuListener { menu, _, _ ->
                onCreateCardSetMenu(menu, cardSet)
            }
            itemView.container.isLongClickable = !selectionMode
            itemView.cardSetNameTextView.text = cardSet.name

            itemView.cardSetCheckBox.setVisible(selectionMode)
            itemView.cardSetCheckBox.isChecked = isItemPositionSelected(adapterPosition)

            itemView.memoryLabelImageView.setVisible(!selectionMode)
            showMemoryLabelIfNeeded(cardSet)
        }

        private fun showMemoryLabelIfNeeded(cardSet: CardSet) {
            if (cardSet.cards.isNotEmpty()) {
                cardSetMemoryLabelTransformer.transform(cardSet).let {
                    itemView.memoryLabelImageView.setBackgroundResource(it!!)
                }
            }
        }

        private fun onCreateCardSetMenu(menu: ContextMenu, cardSet: CardSet) {
            menu.add(R.string.edit).setOnMenuItemClickListener {
                onEditClicked(cardSet)
                true
            }
            menu.add(R.string.delete).setOnMenuItemClickListener {
                onDeleteClicked(cardSet)
                true
            }
        }

        private fun onCardSetClicked(cardSet: CardSet) {
            if (selectionMode) {
                isItemPositionSelected(adapterPosition).also { selected ->
                    if (selected) {
                        selectedItemsPositions.remove(adapterPosition)
                    } else {
                        selectedItemsPositions.add(adapterPosition)
                    }
                    itemView.cardSetCheckBox.isChecked = !selected
                    onCardSetSelected(cardSet, !selected)
                }
            } else {
                onCardSetClickListener(cardSet)
            }
        }
    }
}
