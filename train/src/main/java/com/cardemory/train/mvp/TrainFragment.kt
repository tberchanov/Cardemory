package com.cardemory.train.mvp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DefaultItemAnimator
import com.cardemory.carddata.entity.CardSet
import com.cardemory.common.mvp.BaseFragment
import com.cardemory.train.R
import com.cardemory.train.ui.SwipeCardStackItemListener
import com.cardemory.train.ui.TrainCardStackAdapter
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.Duration
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting
import kotlinx.android.synthetic.main.fragment_train.*
import timber.log.Timber

class TrainFragment :
    BaseFragment<TrainContract.View, TrainContract.Presenter>(),
    TrainContract.View {

    private lateinit var cardStackAdapter: TrainCardStackAdapter

    private lateinit var cardStackManager: CardStackLayoutManager

    override val layoutResId = R.layout.fragment_train

    override fun onAttach(context: Context) {
        super.onAttach(context)
        cardStackAdapter = TrainCardStackAdapter(context)
        cardStackManager = CardStackLayoutManager(
            context,
            SwipeCardStackItemListener(::onCardSwiped)
        )
    }

    private fun onCardSwiped(cardPosition: Int, direction: Direction) {
        val cardsList = getCardsList()
        val swipedCard = cardsList[cardPosition]
        when (direction) {
            Direction.Right -> presenter.onCardRemembered(swipedCard)
            Direction.Left -> presenter.onCardForgot(swipedCard)
            else -> Timber.d("Unexpected swipe direction: $direction")
        }
        if (cardPosition == cardsList.size - 1) {
            presenter.onLastCardSwiped(cardsList)
        }
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

    override fun onDestroyView() {
        cardStackAdapter.removeAnimationListeners()
        super.onDestroyView()
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
        cardStackAdapter.swapData(getCardsList())

        cardStackManager.apply {
            setVisibleCount(VISIBLE_TRAIN_CARDS_COUNT)
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

    private fun getCardsList() = getCardSetArg().cards.values.toList()

    override fun showFinishMessage(resultMemoryRank: Double) {
        // TODO show correct message according to ux/ui. Use resources.
        context?.also {
            AlertDialog.Builder(it)
                .setTitle("Train is finished!")
                .setMessage("Your result memory rank is: $resultMemoryRank")
                .setPositiveButton("OK") { _, _ -> presenter.onFinishMessageConfirmed() }
                .setCancelable(false)
                .create()
                .show()
        }
    }

    companion object {

        private const val VISIBLE_TRAIN_CARDS_COUNT = 2

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
