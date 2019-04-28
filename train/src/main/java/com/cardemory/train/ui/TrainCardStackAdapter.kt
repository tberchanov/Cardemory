package com.cardemory.train.ui

import android.view.View
import com.cardemory.carddata.entity.Card
import com.cardemory.common.ui.BaseAdapter
import com.cardemory.common.ui.BaseHolder
import com.cardemory.train.R
import com.cardemory.train.ui.TrainCardStackAdapter.TrainCardStackHolder
import kotlinx.android.synthetic.main.item_train_card_stack.view.*

class TrainCardStackAdapter : BaseAdapter<Card, TrainCardStackHolder>() {

    override fun layoutId(viewType: Int) = R.layout.item_train_card_stack

    override fun getHolder(view: View, viewType: Int) = TrainCardStackHolder(view)

    inner class TrainCardStackHolder(itemView: View) : BaseHolder<Card>(itemView) {

        override fun bind(uiEntity: Card, position: Int) {
            super.bind(uiEntity, position)
            itemView.trainCardTitleTextView.text = uiEntity.title
        }
    }
}
