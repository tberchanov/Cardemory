package com.cardemory.train.ui

import android.view.View
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import timber.log.Timber

class SwipeCardStackItemListener(
    private val onItemSwiped: (itemPosition: Int, swipeDirection: Direction) -> Unit
) : CardStackListener {

    private var appearedItemPosition = -1

    override fun onCardDisappeared(view: View?, position: Int) {
        Timber.d("onCardDisappeared view: $view position: $position")
    }

    override fun onCardDragging(direction: Direction, ratio: Float) {
        Timber.d("onCardDragging direction: $direction ratio: $ratio")
    }

    override fun onCardSwiped(direction: Direction) {
        Timber.d("onCardSwiped direction: $direction")
        onItemSwiped(appearedItemPosition, direction)
    }

    override fun onCardCanceled() {
        Timber.d("onCardCanceled")
    }

    override fun onCardAppeared(view: View?, position: Int) {
        Timber.d("onCardAppeared: view: $view position: $position")
        appearedItemPosition = position
    }

    override fun onCardRewound() {
        Timber.d("onCardRewound")
    }
}