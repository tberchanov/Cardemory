package com.cardemory.cardlist.mvp

import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.entity.CardSet
import com.cardemory.carddata.interactor.GetCardSetInteractor
import com.cardemory.carddata.interactor.SaveCardSetToFileInteractor
import com.cardemory.cardlist.R
import com.cardemory.cardlist.mvp.CardListContract.Companion.REQUIRED_CARDS_FOR_TRAIN
import com.cardemory.cardlist.navigation.CardListNavigation
import com.cardemory.common.mvp.BasePresenter
import com.cardemory.common.util.ProgressInteractorExecutor
import com.cardemory.infrastructure.entity.Failure
import kotlinx.coroutines.ObsoleteCoroutinesApi
import timber.log.Timber
import java.io.File
import kotlin.random.Random

@ObsoleteCoroutinesApi
class CardListPresenter constructor(
    private val navigation: CardListNavigation,
    private val getCardSetInteractor: GetCardSetInteractor,
    private val saveCardSetToFileInteractor: SaveCardSetToFileInteractor,
    private val progressInteractorExecutor: ProgressInteractorExecutor
) : BasePresenter<CardListContract.View>(),
    CardListContract.Presenter {

    override fun onCreateCardClicked(cardSet: CardSet) {
        navigation.showCreateCardScreen(cardSet)
    }

    override fun onCardClicked(card: Card) {
        navigation.showEditCardScreen(card)
    }

    override fun onCardSaved(card: Card) {
        view?.showNewCard(card)
    }

    override fun onTrainClicked(cardSet: CardSet) {
        val cardsCount = cardSet.cards.size
        if (cardsCount < REQUIRED_CARDS_FOR_TRAIN) {
            view?.showNotEnoughCardsMessage(REQUIRED_CARDS_FOR_TRAIN - cardsCount)
        } else {
            navigation.showTrainScreen(selectCardsForTrain(cardSet))
        }
    }

    private fun selectCardsForTrain(cardSet: CardSet): CardSet {
        val cards = cardSet
            .cards
            .values
            .map(::invertCardMemoryRank)
            .sortedByDescending { it.memoryRank }
            .toMutableList()

        val selectedCards = mutableSetOf<Card>()
        while (selectedCards.size < REQUIRED_CARDS_FOR_TRAIN) {
            val memoryRankSum = cards.sumByDouble { it.memoryRank }
            val randomSelector = when (memoryRankSum) {
                0.0 -> 0.0
                else -> Random.nextDouble(memoryRankSum)
            }

            var currentMemoryRank = 0.0
            for (card in cards) {
                currentMemoryRank += card.memoryRank
                if (randomSelector <= currentMemoryRank) {
                    cardSet.cards.get(card.id)?.let {
                        selectedCards.add(it)
                    }
                    cards.remove(card)
                    break
                }
            }
        }
        return cardSet.copy(
            cards = selectedCards.associate { it.id to it }
        )
    }


    private fun invertCardMemoryRank(card: Card): Card {
        return card.copy(memoryRank = 1 - card.memoryRank)
    }

    override fun loadCardSet(cardSet: CardSet) {
        getCardSetInteractor(cardSet.id) {
            it.either(::onLoadCardSetFailure, ::onLoadCardSetSuccess)
        }
    }

    private fun onLoadCardSetSuccess(cardSet: CardSet) {
        view?.showCardSetData(cardSet)
    }

    private fun onLoadCardSetFailure(failure: Failure) {
        Timber.e("onLoadCardSetFailure: $failure")
    }

    override fun onPermissionDenied() {
        view?.showExportDeniedPermissionMessage()
    }

    override fun exportCardSet(cardSet: CardSet) {
        progressInteractorExecutor.executeWithProgress(
            R.string.exporting,
            saveCardSetToFileInteractor,
            cardSet
        ) {
            it.either(::onExportingFailure, ::onExportingSuccess)
        }
    }

    override fun showAppSettings() {
        navigation.showAppSettings()
    }

    private fun onExportingFailure(failure: Failure) {
        Timber.e("onExportingFailure: $failure")
    }

    private fun onExportingSuccess(exportedFile: File) {
        view?.showSuccessExportingMessage(exportedFile.path)
    }
}
