package com.cardemory.train.mvp

import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.interactor.SaveCardsInteractor
import com.cardemory.common.mvp.BasePresenter
import com.cardemory.infrastructure.entity.Failure
import com.cardemory.memorykit.MemoryManager
import com.cardemory.train.navigation.TrainNavigation
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
        val initialMemoryRank = getTotalMemoryRank(cardsList)
        val trainedMemoryRank = getTotalMemoryRank(trainedCards)
        view?.showFinishMessage(trainedMemoryRank - initialMemoryRank)
    }

    private fun getTotalMemoryRank(cards: List<Card>): Double {
        var totalMemoryRank = 0.0
        cards.forEach {
            totalMemoryRank += it.memoryRank
        }
        return totalMemoryRank
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
}
