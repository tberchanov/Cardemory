package com.cardemory.cardsetlist.mvp.cardsetlist

import com.cardemory.carddata.entity.CardSet
import com.cardemory.carddata.interactor.DeleteCardSetsInteractor
import com.cardemory.carddata.interactor.GetAllCardSetsInteractor
import com.cardemory.cardsetlist.navigation.CardSetListNavigation
import com.cardemory.common.interactor.ReadBooleanInteractor
import com.cardemory.common.interactor.WriteBooleanInteractor
import com.cardemory.common.mvp.BasePresenter
import com.cardemory.infrastructure.entity.Failure
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class CardSetListPresenter(
    private val cardSetListNavigation: CardSetListNavigation,
    private val getAllCardSetsInteractor: GetAllCardSetsInteractor,
    private val deleteCardSetsInteractor: DeleteCardSetsInteractor,
    private val writeBooleanInteractor: WriteBooleanInteractor,
    private val readBooleanInteractor: ReadBooleanInteractor
) : BasePresenter<CardSetListContract.View>(),
    CardSetListContract.Presenter {

    override fun attachView(view: CardSetListContract.View) {
        super.attachView(view)
        loadCardSets()
        checkPreviousVisit()
    }

    private fun checkPreviousVisit() {
        readBooleanInteractor(PREVIOUS_VISIT_CARD_SET_LIST_KEY) { either ->
            either.either(
                { Timber.e("checkPreviousVisit failure: $it") },
                ::onReadPreviousVisitSuccess
            )
        }
    }

    private fun onReadPreviousVisitSuccess(previousVisit: Boolean) {
        Timber.d("onReadPreviousVisitSuccess: $previousVisit")
        if (!previousVisit) {
            view?.showWelcomeMessage()
        }
    }

    private fun loadCardSets() {
        launch {
            // delay to show prepopulated data
            delay(LOAD_CARD_SETS_DELAY_MILLIS)
            getAllCardSetsInteractor(Unit) {
                it.either(::onGetAllCardSetsFailure, ::onGetAllCardSetsSuccess)
            }
        }
    }

    private fun onGetAllCardSetsFailure(f: Failure) {
        Timber.e("onGetAllCardSetsFailure: $f")
    }

    private fun onGetAllCardSetsSuccess(cardSets: List<CardSet>) {
        Timber.d("onGetAllCardSetsSuccess: $cardSets")
        view?.showCardSets(cardSets)
    }

    override fun onCreateCardSetClicked() {
        cardSetListNavigation.showCreateCardSetScreen()
    }

    override fun onCardSetClicked(cardSet: CardSet) {
        cardSetListNavigation.showCardsScreen(cardSet)
    }

    override fun onPrivacyPolicyClicked() {
        cardSetListNavigation.showPrivacyPolicyScreen()
    }

    override fun onEditCardSetClicked(cardSet: CardSet) {
        cardSetListNavigation.showEditCardSetScreen(cardSet)
    }

    override fun onDeleteCardSetClicked(cardSet: CardSet) {
        view?.setSelectionModeEnabled(true)
        view?.selectCardSetForDeletion(cardSet)
    }

    override fun onDeleteCardSetsClicked(cardSets: List<CardSet>) {
        deleteCardSetsInteractor(cardSets) {
            it.either(::onCardSetsDeleteFailure, ::onCardSetsDeleteSuccess)
        }
    }

    private fun onCardSetsDeleteFailure(failure: Failure) {
        Timber.e("onCardSetsDeleteFailure: $failure")
    }

    private fun onCardSetsDeleteSuccess(cardSets: List<CardSet>) {
        view?.setSelectionModeEnabled(false)
        view?.clearCardSets(cardSets)
    }

    override fun onCardSetSelected(cardSet: CardSet, selected: Boolean) {
        Timber.d("onCardSetSelected $selected: $cardSet")
        if (view?.getSelectedItemsCount() == 0) {
            view?.setSelectionModeEnabled(false)
        } else {
            view?.showSelectionTitle()
        }
    }

    override fun onStartTutorialClicked() {
        savePreviousVisitCardSetList()
        saveShowTutorial()
        view?.showTutorialActionButton()
    }

    private fun saveShowTutorial() {
        writeBooleanInteractor(SHOW_TUTORIAL_KEY, true) {
            it.either(::onSaveShowTutorialFailure, ::onSaveShowTutorialSuccess)
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onSaveShowTutorialSuccess(ignore: Unit) {
        Timber.d("onSaveShowTutorialSuccess")
    }

    private fun onSaveShowTutorialFailure(failure: Failure) {
        Timber.d("onSaveShowTutorialFailure: $failure")
    }

    override fun onSkipTutorialClicked() {
        savePreviousVisitCardSetList()
    }

    private fun savePreviousVisitCardSetList() {
        writeBooleanInteractor(
            PREVIOUS_VISIT_CARD_SET_LIST_KEY,
            true
        ) {
            it.either(
                ::savePreviousVisitCardSetListFailure,
                ::savePreviousVisitCardSetListSuccess
            )
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun savePreviousVisitCardSetListSuccess(ignore: Unit) {
        Timber.d("savePreviousVisitCardSetListSuccess")
    }

    private fun savePreviousVisitCardSetListFailure(failure: Failure) {
        Timber.d("savePreviousVisitCardSetListFailure: $failure")
    }

    companion object {
        private const val LOAD_CARD_SETS_DELAY_MILLIS = 800L

        private const val PREVIOUS_VISIT_CARD_SET_LIST_KEY = "PREVIOUS_VISIT_CARD_SET_LIST"

        private const val SHOW_TUTORIAL_KEY = "SHOW_TUTORIAL"
    }
}
