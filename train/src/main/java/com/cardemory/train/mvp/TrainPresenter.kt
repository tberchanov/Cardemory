package com.cardemory.train.mvp

import com.cardemory.carddata.entity.Card
import com.cardemory.common.mvp.BasePresenter
import com.cardemory.train.navigation.TrainNavigation

class TrainPresenter(
    private val trainNavigation: TrainNavigation
) : BasePresenter<TrainContract.View>(),
    TrainContract.Presenter {

    private val trainedCards = mutableListOf<Card>()

    override fun onCardRemembered(card: Card) {
        val trainedCard = card.copy(
            memoryRank = calculateRememberedCardMemoryRank(card),
            lastTrainMillis = System.currentTimeMillis()
        )
        trainedCards.add(trainedCard)
    }

    // +10 up to 100
    private fun calculateRememberedCardMemoryRank(card: Card) =
        (card.memoryRank + 10).takeUnless { it > 100 } ?: 100.0

    override fun onCardForgot(card: Card) {
        val trainedCard = card.copy(
            memoryRank = calculateForgottenCardMemoryRank(card),
            lastTrainMillis = System.currentTimeMillis()
        )
        trainedCards.add(trainedCard)
    }

    // -10 down to 0
    private fun calculateForgottenCardMemoryRank(card: Card) =
        (card.memoryRank - 10).takeUnless { it < 0 } ?: 0.0

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
        // todo save trainedCards
        trainNavigation.back()
    }
}
