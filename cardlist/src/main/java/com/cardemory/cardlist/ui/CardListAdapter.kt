package com.cardemory.cardlist.ui

import android.view.View
import androidx.core.content.ContextCompat
import com.cardemory.carddata.entity.Card
import com.cardemory.cardlist.R
import com.cardemory.common.ui.BaseHolder
import com.cardemory.common.ui.BaseSelectableAdapter
import com.cardemory.common.util.ext.setVisible
import com.cardemory.memory_label.CardMemoryLabelTransformer
import kotlinx.android.synthetic.main.item_card.view.*
import kotlinx.android.synthetic.main.item_train.view.*

// TODO refactor, problems with adapter positions and TRAIN_CARD_ITEM_OFFSET
class CardListAdapter(
    private val onCardClickListener: (card: Card) -> Unit,
    private val onTrainClickListener: () -> Unit,
    private val requiredCardsForTrain: Int,
    private val onDeleteClicked: (Card) -> Unit,
    private val onCardSelectListener: (Card, selected: Boolean) -> Unit,
    private val cardMemoryLabelTransformer: CardMemoryLabelTransformer
) : BaseSelectableAdapter<Card, BaseHolder<Card>>(onCardSelectListener) {

    override fun selectItem(position: Int) {
        super.selectItem(position + TRAIN_CARD_ITEM_OFFSET)
    }

    override fun setSelectionModeEnabled(enabled: Boolean) {
        super.setSelectionModeEnabled(enabled)
        notifyItemRangeChanged(itemCount, TRAIN_CARD_ITEM_OFFSET, Unit)
    }

    override fun unSelectItem(position: Int) {
        super.unSelectItem(position + TRAIN_CARD_ITEM_OFFSET)
    }

    override fun swapData(items: List<Card>, withNotify: Boolean) {
        super.swapData(listOf(Card(), *items.toTypedArray()), withNotify)
    }

    override fun getItems(): List<Card> {
        return super.getItems().drop(TRAIN_CARD_ITEM_OFFSET)
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

    override fun getSelectedItems(): List<Card> {
        val adapterItems = getItems()
        return selectedItemsPositions
            .map { it - TRAIN_CARD_ITEM_OFFSET }
            .map(adapterItems::get)
    }

    inner class CardHolder(itemView: View) : BaseHolder<Card>(itemView) {

        override fun bind(uiEntity: Card, position: Int) {
            super.bind(uiEntity, position)
            itemView.titleTextView.text = uiEntity.title
            itemView.descriptionTextView.text = uiEntity.description
            itemView.cardContainer.setOnClickListener {
                onCardClicked(uiEntity)
            }
            itemView.cardContainer.setOnCreateContextMenuListener { menu, _, _ ->
                menu.add(R.string.delete).setOnMenuItemClickListener {
                    onDeleteClicked(uiEntity)
                    true
                }
            }
            itemView.cardContainer.isLongClickable = !selectionMode

            itemView.cardCheckBox.setVisible(selectionMode)
            itemView.cardCheckBox.isChecked = isItemPositionSelected(adapterPosition)

            itemView.memoryLabelImageView.setVisible(!selectionMode)
            cardMemoryLabelTransformer.transform(uiEntity).let {
                itemView.memoryLabelImageView.setBackgroundResource(it!!)
            }
        }

        private fun onCardClicked(card: Card) {
            if (selectionMode) {
                isItemPositionSelected(adapterPosition).also { selected ->
                    if (selected) {
                        selectedItemsPositions.remove(adapterPosition)
                    } else {
                        selectedItemsPositions.add(adapterPosition)
                    }
                    itemView.cardCheckBox.isChecked = !selected
                    onCardSelectListener(card, !selected)
                }
            } else {
                onCardClickListener(card)
            }
        }
    }

    inner class TrainHolder(itemView: View) : BaseHolder<Card>(itemView) {

        override fun bind(uiEntity: Card, position: Int) {
            super.bind(uiEntity, position)
            itemView.trainButton.setOnClickListener {
                onTrainClickListener()
            }
            val backgroundColor = ContextCompat.getColor(
                itemView.context,
                getTrainButtonBackgroundColorRes()
            )
            itemView.trainButton.setCardBackgroundColor(backgroundColor)
        }

        private fun getTrainButtonBackgroundColorRes() =
            if (itemCount <= requiredCardsForTrain)
                R.color.silver
            else
                R.color.fruit_salad
    }

    companion object {
        private const val ITEM_TRAIN = 0
        private const val ITEM_CARD = 1
        private const val TRAIN_CARD_ITEM_OFFSET = 1
    }
}