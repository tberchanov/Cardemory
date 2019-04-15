package com.cardemory.cardsetlist.ui

import android.view.View
import com.cardemory.carddata.entity.CardSet
import com.cardemory.cardsetlist.R
import com.cardemory.common.ui.BaseAdapter
import com.cardemory.common.ui.BaseHolder
import kotlinx.android.synthetic.main.item_card_set.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CardSetListAdapter(
    private val onCardSetClickListener: (card: CardSet) -> Unit
) : BaseAdapter<CardSet, CardSetListAdapter.CardSetHolder>() {

    override fun layoutId(viewType: Int) = R.layout.item_card_set

    override fun getHolder(view: View, viewType: Int) = CardSetHolder(view)

    inner class CardSetHolder(itemView: View) : BaseHolder<CardSet>(itemView) {

        override fun bind(uiEntity: CardSet, position: Int) {
            super.bind(uiEntity, position)
            itemView.cardSetContainer.setOnClickListener {
                onCardSetClickListener(uiEntity)
            }
            GlobalScope.launch(Dispatchers.Main) {
                itemView.cardSetContainer.setCardsCount(CARDSET_CARDS_COUNT)
                itemView.cardSetContainer.setText(uiEntity.name)
            }
        }
    }

    companion object {
        private const val CARDSET_CARDS_COUNT = 3
    }
}