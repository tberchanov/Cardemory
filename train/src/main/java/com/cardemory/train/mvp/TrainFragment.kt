package com.cardemory.train.mvp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.DefaultItemAnimator
import com.cardemory.carddata.entity.CardSet
import com.cardemory.common.mvp.BaseFragment
import com.cardemory.train.R
import com.cardemory.train.ui.TrainCardStackAdapter
import com.yuyakaido.android.cardstackview.*
import kotlinx.android.synthetic.main.fragment_train.*
import timber.log.Timber

class TrainFragment :
    BaseFragment<TrainContract.View, TrainContract.Presenter>(),
    TrainContract.View {

    private val cardStackAdapter = TrainCardStackAdapter()

    private lateinit var cardStackManager: CardStackLayoutManager

    override val layoutResId = R.layout.fragment_train

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        cardStackManager = CardStackLayoutManager(context, TrainCardStackListener())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCardStackView()
        forgotButton.setOnClickListener {
            swipeCard(Direction.Left)
        }
        rememberedButton.setOnClickListener {
            swipeCard(Direction.Right)
        }
    }

    private fun swipeCard(direction: Direction) {
        setCardStackAnimationDirection(cardStackManager, direction)
        cardStackView.swipe()
    }

    private fun setCardStackAnimationDirection(
        cardStackLayoutManager: CardStackLayoutManager,
        direction: Direction
    ) {
        val setting = SwipeAnimationSetting.Builder()
            .setDirection(direction)
            .setDuration(Duration.Slow.duration)
            .setInterpolator(AccelerateInterpolator())
            .build()
        cardStackLayoutManager.setSwipeAnimationSetting(setting)
    }

    private fun initCardStackView() {
        cardStackAdapter.swapData(
            getCardSetArg().cards.values.toList()
        )

        cardStackManager.apply {
            setVisibleCount(2)
            setDirections(Direction.HORIZONTAL)
            setCanScrollHorizontal(true)
            setCanScrollVertical(false)
            setOverlayInterpolator(LinearInterpolator())
        }
        cardStackView.layoutManager = cardStackManager
        cardStackView.adapter = cardStackAdapter
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    private inner class TrainCardStackListener : CardStackListener {
        override fun onCardDisappeared(view: View?, position: Int) {
            Timber.d("onCardDisappeared view: $view position: $position")
        }

        override fun onCardDragging(direction: Direction?, ratio: Float) {
            Timber.d("onCardDragging direction: $direction ratio: $ratio")
        }

        override fun onCardSwiped(direction: Direction?) {
            Timber.d("onCardSwiped direction: $direction")
        }

        override fun onCardCanceled() {
            Timber.d("onCardCanceled")
        }

        override fun onCardAppeared(view: View?, position: Int) {
            Timber.d("onCardAppeared: view: $view position: $position")
        }

        override fun onCardRewound() {
            Timber.d("onCardRewound")
        }
    }

    companion object {

        private const val CARD_SET_KEY = "CARD_SET_KEY"

        fun newInstance(cardSet: CardSet): TrainFragment {
            val fragment = TrainFragment()
            fragment.putCardSetArg(cardSet)
            return fragment
        }

        private fun TrainFragment.putCardSetArg(cardSet: CardSet) {
            arguments = Bundle().apply {
                putParcelable(CARD_SET_KEY, cardSet)
            }
        }

        private fun TrainFragment.getCardSetArg(): CardSet =
            arguments!!.getParcelable(CARD_SET_KEY)!!
    }
}
