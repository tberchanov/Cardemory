package com.cardemory.cardlist.ui

import android.view.View
import com.cardemory.carddata.entity.Card
import com.cardemory.cardlist.R
import com.cardemory.common.ui.BaseAdapter
import com.cardemory.common.ui.BaseHolder
import kotlinx.android.synthetic.main.item_card.view.*
import kotlinx.android.synthetic.main.item_train.view.*

class CardListAdapter(
    private val onCardClickListener: (card: Card) -> Unit,
    private val onTrainClickListener: () -> Unit
) : BaseAdapter<Card, BaseHolder<Card>>() {

    override fun swapData(items: List<Card>, withNotify: Boolean) {
        super.swapData(listOf(Card(), *items.toTypedArray()), withNotify)
    }

    override fun getItems(): List<Card> {
        return super.getItems().drop(1)
    }

    override fun getItemViewType(position: Int) =
        if (position == 0) ITEM_TRAIN else ITEM_CARD

    override fun layoutId(viewType: Int) = when (viewType) {
        ITEM_TRAIN -> R.layout.item_train
        ITEM_CARD -> R.layout.item_card
        else -> 0
    }

    override fun getHolder(view: View, viewType: Int) = when (viewType) {
        ITEM_TRAIN -> TrainHolder(view)
        ITEM_CARD -> CardHolder(view)
        else -> throw IllegalStateException("Illegal item type: $viewType")
    }

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

    inner class TrainHolder(itemView: View) : BaseHolder<Card>(itemView) {

        override fun bind(uiEntity: Card, position: Int) {
            super.bind(uiEntity, position)
            itemView.trainButton.setOnClickListener {
                onTrainClickListener()
            }
        }
    }

    companion object {
        private const val ITEM_TRAIN = 0
        private const val ITEM_CARD = 1
    }
}