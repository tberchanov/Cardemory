package com.cardemory.cardlist.ui

import androidx.recyclerview.widget.DiffUtil
import com.cardemory.carddata.entity.Card

class CardDiffUtilCallback(
    private val oldCards: List<Card>,
    private val newCards: List<Card>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldCards.size

    override fun getNewListSize() = newCards.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ) = oldCards[oldItemPosition] === newCards[newItemPosition]

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ) = oldCards[oldItemPosition] == newCards[newItemPosition]
}