package com.cardemory.train.mvp

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.interactor.SaveCardsInteractor
import com.cardemory.common.mvp.BasePresenter
import com.cardemory.infrastructure.entity.Failure
import com.cardemory.memorykit.MemoryManager
import com.cardemory.train.R
import com.cardemory.train.navigation.TrainNavigation
import com.cardemory.train.ui.model.TrainCard
import com.cardemory.train.ui.widget.StarState
import com.cardemory.train.ui.widget.StarState.*
import timber.log.Timber

class TrainPresenter(
    private val trainNavigation: TrainNavigation,
    private val memoryManager: MemoryManager,
    private val saveCardsInteractor: SaveCardsInteractor
) : BasePresenter<TrainContract.View>(),
    TrainContract.Presenter {

    private val trainedCards = mutableListOf<Card>()

    override fun onCardRemembered(card: Card) {
        val trainedCard = card.copy(
            memoryRank = memoryManager.remember(card.toCardHolder()),
            lastTrainMillis = System.currentTimeMillis()
        )
        trainedCards.add(trainedCard)
    }

    override fun onCardForgot(card: Card) {
        val trainedCard = card.copy(
            memoryRank = memoryManager.forget(card.toCardHolder()),
            lastTrainMillis = System.currentTimeMillis()
        )
        trainedCards.add(trainedCard)
    }

    private fun Card.toCardHolder() = CardHolder(this)

    override fun onLastCardSwiped(cardsList: List<Card>) {
        getRememberedCardsCount(cardsList, trainedCards).also {
            view?.showFinishMessage(
                getFinishMessageTitle(it),
                getFinishMessageDescription(it),
                getFinishMessageTextColor(it),
                *getStarStates(it)
            )
        }
    }

    @ColorRes
    private fun getFinishMessageTextColor(rememberedCardsCount: Int) =
        if (rememberedCardsCount <= 5)
            R.color.pomegranate
        else
            R.color.fruit_salad

    @StringRes
    private fun getFinishMessageDescription(rememberedCardsCount: Int) =
        if (rememberedCardsCount <= 5) {
            R.string.bad_result_advice
        } else {
            R.string.empty
        }

    @StringRes
    private fun getFinishMessageTitle(rememberedCardsCount: Int) =
        when (rememberedCardsCount) {
            in 0..5 -> R.string.insufficiently
            in 6..8 -> R.string.good
            in 9..10 -> R.string.great
            else -> {
                Timber.d("Unexpected rememberedCardsCount: $rememberedCardsCount")
                R.string.insufficiently
            }
        }

    private fun getStarStates(rememberedCardsCount: Int): Array<StarState> {
        return when (rememberedCardsCount) {
            0 -> arrayOf(OUTLINE, OUTLINE, OUTLINE)
            in 1..2 -> arrayOf(HALF, OUTLINE, OUTLINE)
            in 3..4 -> arrayOf(FULL, OUTLINE, OUTLINE)
            in 5..7 -> arrayOf(FULL, HALF, OUTLINE)
            in 8..9 -> arrayOf(FULL, FULL, HALF)
            10 -> arrayOf(FULL, FULL, FULL)
            else -> {
                Timber.d("Unexpected rememberedCardsCount: $rememberedCardsCount")
                arrayOf(OUTLINE, OUTLINE, OUTLINE)
            }
        }
    }

    private fun getRememberedCardsCount(
        initialCards: List<Card>,
        trainedCards: List<Card>
    ): Int {
        var rememberedCardsCount = 0
        trainedCards.forEachIndexed { index, trainedCard ->
            if (trainedCard.memoryRank > initialCards[index].memoryRank) {
                rememberedCardsCount++
            }
        }
        Timber.d("initial cards: $initialCards")
        Timber.d("trained cards: $trainedCards")
        Timber.d("rememberedCardsCount: $rememberedCardsCount")
        return rememberedCardsCount
    }

    override fun onFinishMessageConfirmed() {
        saveCardsInteractor(trainedCards) {
            it.either(::onSaveCardsFailure) { onSaveCardsSuccess() }
        }
    }

    private fun onSaveCardsSuccess() {
        Timber.d("onSaveCardsSuccess")
        trainNavigation.back()
    }

    private fun onSaveCardsFailure(failure: Failure) {
        Timber.e("onSaveCardFailure: $failure")
    }

    override fun onBackClicked() {
        view?.showBackMessage()
    }

    override fun onBackMessageConfirmed() {
        trainNavigation.back()
    }

    override fun onTrainCardLongPressed(trainCard: TrainCard) {
        Timber.d("onTrainCardLongPressed: $trainCard")
        view?.showCardContent(trainCard)
    }
}
