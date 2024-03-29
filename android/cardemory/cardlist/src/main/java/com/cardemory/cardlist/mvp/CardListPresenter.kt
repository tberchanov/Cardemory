package com.cardemory.cardlist.mvp

import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.entity.CardSet
import com.cardemory.carddata.interactor.DeleteCardsInteractor
import com.cardemory.carddata.interactor.GetCardSetInteractor
import com.cardemory.carddata.interactor.SaveCardSetToFileInteractor
import com.cardemory.cardlist.R
import com.cardemory.cardlist.mvp.CardListContract.Companion.REQUIRED_CARDS_FOR_TRAIN
import com.cardemory.cardlist.navigation.CardListNavigation
import com.cardemory.cardlist.ui.tutorial.CardListTutorialSpotlight
import com.cardemory.common.interactor.ReadBooleanInteractor
import com.cardemory.common.interactor.WriteBooleanInteractor
import com.cardemory.common.mvp.BasePresenter
import com.cardemory.common.util.ProgressInteractorExecutor
import com.cardemory.infrastructure.entity.Failure
import com.cardemory.memory_label.CardMemoryLabelTransformer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import kotlin.random.Random

@ObsoleteCoroutinesApi
class CardListPresenter constructor(
    private val navigation: CardListNavigation,
    private val getCardSetInteractor: GetCardSetInteractor,
    private val saveCardSetToFileInteractor: SaveCardSetToFileInteractor,
    private val deleteCardsInteractor: DeleteCardsInteractor,
    private val progressInteractorExecutor: ProgressInteractorExecutor,
    private val readBooleanInteractor: ReadBooleanInteractor,
    private val writeBooleanInteractor: WriteBooleanInteractor,
    override val cardListTutorialSpotlight: CardListTutorialSpotlight,
    override val cardMemoryLabelTransformer: CardMemoryLabelTransformer
) : BasePresenter<CardListContract.View>(),
    CardListContract.Presenter {

    private suspend fun checkPreviousVisit() = withContext(Dispatchers.IO) {
        val showTutorial = readBooleanInteractor.run(SHOW_TUTORIAL_KEY).valueRight
        if (showTutorial) {
            val previouslyVisited =
                readBooleanInteractor.run(PREVIOUS_VISIT_CARD_LIST_KEY).valueRight
            if (!previouslyVisited) {
                withContext(Dispatchers.Main) {
                    view?.showTutorial()
                }
                writeBooleanInteractor.run(
                    WriteBooleanInteractor.Params(PREVIOUS_VISIT_CARD_LIST_KEY, true)
                )
            }
        }
    }

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
                    cardSet.cards[card.id]?.let {
                        selectedCards.add(it)
                    }
                    cards.remove(card)
                    break
                }
            }
        }
        return cardSet.copy(
            cards = selectedCards.associateBy { it.id }
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
        launch(Dispatchers.Main) {
            checkPreviousVisit()
        }
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

    override fun onDeleteCardClicked(card: Card) {
        view?.setSelectionModeEnabled(true)
        view?.selectCardForDeletion(card)
    }

    override fun onCardSelected(card: Card, selected: Boolean) {
        Timber.d("onCardSelected $selected: $card")
        if (view?.getSelectedItemsCount() == 0) {
            view?.setSelectionModeEnabled(false)
        } else {
            view?.showSelectionTitle()
        }
    }

    override fun onDeleteCardsClicked(cards: List<Card>) {
        deleteCardsInteractor(cards) {
            it.either(::onCardsDeleteFailure, ::onCardsDeleteSuccess)
        }
    }

    private fun onCardsDeleteFailure(failure: Failure) {
        Timber.e("onCardsDeleteFailure: $failure")
    }

    private fun onCardsDeleteSuccess(cardSets: List<Card>) {
        view?.setSelectionModeEnabled(false)
        view?.clearCards(cardSets)
    }

    companion object {
        private const val PREVIOUS_VISIT_CARD_LIST_KEY = "PREVIOUS_VISIT_CARD_LIST"
        private const val SHOW_TUTORIAL_KEY = "SHOW_TUTORIAL"
    }
}
