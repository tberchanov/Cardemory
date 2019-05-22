package com.cardemory.cardlist.mvp

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.entity.CardSet
import com.cardemory.cardlist.R
import com.cardemory.cardlist.mvp.CardListContract.Companion.REQUIRED_CARDS_FOR_TRAIN
import com.cardemory.cardlist.ui.CardDiffUtilCallback
import com.cardemory.cardlist.ui.CardListAdapter
import com.cardemory.common.mvp.BaseFragment
import com.cardemory.common.navigation.OnResultListener
import com.cardemory.common.util.EmptyMessageObserver
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_cardlist.*
import timber.log.Timber

class CardListFragment :
    BaseFragment<CardListContract.View, CardListContract.Presenter>(),
    CardListContract.View,
    OnResultListener {

    private lateinit var cardAdapter: CardListAdapter

    private lateinit var emptyMessageObserver: EmptyMessageObserver

    override val layoutResId = R.layout.fragment_cardlist

    override val title by lazy { getCardSetArg().name }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cardSetArg = getCardSetArg()
        createCardButton.setOnClickListener {
            presenter.onCreateCardClicked(cardSetArg)
        }
        setupCardRecyclerView()
        presenter.loadCardSet(cardSetArg)
    }

    override fun showCardSetData(cardSet: CardSet) {
        firstCardLabel.text = getString(R.string.create_first_card_format, cardSet.name)
        showCards(cardSet.cards.values.toList())
    }

    private fun showCards(cards: List<Card>) {
        cardAdapter.swapData(cards)
        if (cards.isEmpty()) {
            setEmptyCardsMessageVisibility(true)
        }
    }

    private fun setupCardRecyclerView() {
        cardAdapter = CardListAdapter(
            ::onCardClicked,
            ::onTrainClicked,
            REQUIRED_CARDS_FOR_TRAIN
        )
        cardsRecyclerView.adapter = cardAdapter
        emptyMessageObserver = EmptyMessageObserver(
            cardAdapter,
            firstCardLabel,
            createCardArrowImageView
        )
        cardAdapter.registerAdapterDataObserver(emptyMessageObserver)
    }

    private fun onCardClicked(card: Card) {
        presenter.onCardClicked(card)
    }

    private fun onTrainClicked() {
        val cardsMap = cardAdapter.getItems().associate { it.id to it }
        val currentCardSet = getCardSetArg().copy(cards = cardsMap)
        presenter.onTrainClicked(currentCardSet)
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

    override fun showNewCard(card: Card) {
        setEmptyCardsMessageVisibility(false)

        val oldCards = cardAdapter.getItems()
        val newCards = oldCards.toMutableList().apply {
            saveCard(card)
        }

        val cardDiffUtilCallback = CardDiffUtilCallback(oldCards, newCards)
        val diffResult = DiffUtil.calculateDiff(cardDiffUtilCallback)

        cardAdapter.swapData(newCards, false)
        diffResult.dispatchUpdatesTo(cardAdapter)
    }

    private fun setEmptyCardsMessageVisibility(visible: Boolean) {
        val visibility = if (visible) View.VISIBLE else View.GONE
        firstCardLabel.visibility = visibility
        createCardArrowImageView.visibility = visibility
    }

    private fun MutableList<Card>.saveCard(card: Card) {
        indexOfFirst { it.id == card.id }
            .takeUnless { it == -1 }?.let {
                set(it, card)
            } ?: add(card)
    }

    override fun onDestroyView() {
        cardAdapter.unregisterAdapterDataObserver(emptyMessageObserver)
        super.onDestroyView()
    }

    override fun onResult(resultCode: Int, data: Any?) {
        Timber.d("on card saving result: $data")
        presenter.onCardSaved(data as Card)
    }

    override fun showNotEnoughCardsMessage(neededCardsCount: Int) {
        Snackbar.make(
            createCardButton,
            getString(R.string.not_enough_cards_format, neededCardsCount),
            Snackbar.LENGTH_LONG
        ).show()
    }

    companion object {

        private const val CARD_SET_KEY = "CARD_SET_KEY"

        fun newInstance(cardSet: CardSet): CardListFragment {
            val fragment = CardListFragment()
            fragment.putCardSetArg(cardSet)
            return fragment
        }

        private fun CardListFragment.putCardSetArg(cardSet: CardSet) {
            arguments = Bundle().apply {
                putParcelable(CARD_SET_KEY, cardSet)
            }
        }

        private fun CardListFragment.getCardSetArg(): CardSet =
            arguments!!.getParcelable(CARD_SET_KEY)!!
    }
}