package com.cardemory.cardsetlist.ui

import android.view.View
import com.cardemory.carddata.entity.CardSet
import com.cardemory.cardsetlist.R
import com.cardemory.common.ui.BaseHolder
import com.cardemory.common.ui.BaseSelectableAdapter
import com.cardemory.common.util.setVisible
import kotlinx.android.synthetic.main.item_card_set.view.*

class CardSetListAdapter(
    private val onCardSetClickListener: (CardSet) -> Unit,
    private val onEditClicked: (CardSet) -> Unit,
    private val onDeleteClicked: (CardSet) -> Unit,
    private val onCardSetSelected: (CardSet, selected: Boolean) -> Unit
) : BaseSelectableAdapter<CardSet, CardSetListAdapter.CardSetHolder>(onCardSetSelected) {

    override fun layoutId(viewType: Int) = R.layout.item_card_set

    override fun getHolder(view: View, viewType: Int) = CardSetHolder(view)

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
            itemView.cardSetCheckBox.isChecked = isItemPositionSelected(adapterPosition)
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
