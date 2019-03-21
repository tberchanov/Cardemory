package com.cardemory.cardlist.ui

import android.view.View
import com.cardemory.carddata.entity.Card
import com.cardemory.cardlist.R
import com.cardemory.common.ui.BaseAdapter
import com.cardemory.common.ui.BaseHolder
import kotlinx.android.synthetic.main.item_card.view.*

class CardListAdapter(
    private val onCardClickListener: (card: Card) -> Unit
) : BaseAdapter<Card, CardListAdapter.CardHolder>() {

    override fun layoutId(viewType: Int) = R.layout.item_card

    override fun getHolder(view: View, viewType: Int) = CardHolder(view)

    inner class CardHolder(itemView: View) : BaseHolder<Card>(itemView) {

        override fun bind(uiEntity: Card, position: Int) {
            super.bind(uiEntity, position)
            itemView.titleTextView.text = uiEntity.title
            itemView.descriptionTextView.text = uiEntity.description
            itemView.cardContainer.setOnClickListener {
                onCardClickListener(uiEntity)
            }
        }
    }
}