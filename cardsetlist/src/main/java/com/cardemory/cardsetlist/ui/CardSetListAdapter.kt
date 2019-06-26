package com.cardemory.cardsetlist.ui

import android.view.View
import com.cardemory.carddata.entity.CardSet
import com.cardemory.cardsetlist.R
import com.cardemory.common.ui.BaseAdapter
import com.cardemory.common.ui.BaseHolder
import kotlinx.android.synthetic.main.item_card_set.view.*

class CardSetListAdapter(
    private val onCardSetClickListener: (card: CardSet) -> Unit,
    private val onEditClicked: (card: CardSet) -> Unit,
    private val onDeleteClicked: (card: CardSet) -> Unit
) : BaseAdapter<CardSet, CardSetListAdapter.CardSetHolder>() {

    override fun layoutId(viewType: Int) = R.layout.item_card_set

    override fun getHolder(view: View, viewType: Int) = CardSetHolder(view)

    inner class CardSetHolder(itemView: View) : BaseHolder<CardSet>(itemView) {

        override fun bind(uiEntity: CardSet, position: Int) {
            super.bind(uiEntity, position)
            itemView.container.setOnClickListener {
                onCardSetClickListener(uiEntity)
            }
            itemView.container.setOnCreateContextMenuListener { menu, view, menuInfo ->
                menu.add(R.string.edit).setOnMenuItemClickListener {
                    onEditClicked(uiEntity)
                    true
                }
                menu.add(R.string.delete).setOnMenuItemClickListener {
                    onDeleteClicked(uiEntity)
                    true
                }
            }
            itemView.cardSetNameTextView.text = uiEntity.name
        }
    }
}
