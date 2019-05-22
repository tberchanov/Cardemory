package com.cardemory.train.mvp

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DefaultItemAnimator
import com.cardemory.carddata.entity.CardSet
import com.cardemory.common.mvp.BaseFragment
import com.cardemory.common.mvp.OnBackPressedListener
import com.cardemory.train.R
import com.cardemory.train.ui.FinishTrainDialog
import com.cardemory.train.ui.SwipeCardStackItemListener
import com.cardemory.train.ui.TrainCardStackAdapter
import com.cardemory.train.ui.model.TrainCard
import com.cardemory.train.ui.widget.StarState
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.Duration
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting
import kotlinx.android.synthetic.main.fragment_train.*
import timber.log.Timber


class TrainFragment :
    BaseFragment<TrainContract.View, TrainContract.Presenter>(),
    TrainContract.View, OnBackPressedListener {

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
        cardStackAdapter.swapData(
            getCardsList().map { TrainCard(it) }
        )

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

    override fun showFinishMessage(
        @StringRes finishTrainDialogTitleRes: Int,
        @StringRes finishTrainDialogMessageRes: Int,
        @ColorRes textColorRes: Int,
        vararg starState: StarState
    ) {
        FinishTrainDialog(
            finishTrainDialogTitleRes,
            finishTrainDialogMessageRes,
            textColorRes,
            ::onFinishTrainDialogBackClicked,
            *starState
        ).apply {
            isCancelable = false
        }.show(fragmentManager!!, FINISH_TRAIN_DIALOG_TAG)
    }

    private fun onFinishTrainDialogBackClicked() {
        presenter.onFinishMessageConfirmed()
    }

    override fun onStart() {
        super.onStart()
        Handler().post {
            setBackButtonVisibility(true)
        }
    }

    override fun onStop() {
        setBackButtonVisibility(false)
        super.onStop()
    }

    override fun onBackPressed(): Boolean {
        presenter.onBackClicked()
        return true
    }

    override fun showBackMessage() {
        AlertDialog.Builder(activity!!)
            .setTitle(R.string.train_is_not_finished)
            .setMessage(R.string.answers_wont_be_saved)
            .setPositiveButton(R.string.back) { _, _ ->
                presenter.onBackMessageConfirmed()
            }
            .setNegativeButton(R.string.keep_training, null)
            .create()
            .show()
    }

    companion object {

        private const val FINISH_TRAIN_DIALOG_TAG = "FINISH_TRAIN_DIALOG"

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
