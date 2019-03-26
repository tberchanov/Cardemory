package com.cardemory.cardlist.mvp

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.entity.CardSet
import com.cardemory.cardlist.R
import com.cardemory.cardlist.ui.CardDiffUtilCallback
import com.cardemory.cardlist.ui.CardListAdapter
import com.cardemory.common.mvp.BaseFragment
import com.cardemory.common.navigation.OnResultListener
import kotlinx.android.synthetic.main.fragment_cardlist.*
import timber.log.Timber

class CardListFragment :
    BaseFragment<CardListContract.View, CardListContract.Presenter>(),
    CardListContract.View,
    OnResultListener {

    private var cardAdapter: CardListAdapter? = null

    override val layoutResId = R.layout.fragment_cardlist

    override val title by lazy { getCardSetArg().name }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardSet = readFromActivityStorage(CARD_SET_STORAGE_KEY) ?: getCardSetArg()

        createCardButton.setOnClickListener {
            presenter.onCreateCardClicked(cardSet)
        }

        cardAdapter = CardListAdapter(::onCardClicked)
        cardsRecyclerView.adapter = cardAdapter

        presenter.showCards(cardSet)
    }

    private fun onCardClicked(card: Card) {
        presenter.onCardClicked(card)
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

    override fun showCards(cards: List<Card>) {
        cardAdapter?.swapData(cards)
    }

    override fun showNewCard(card: Card) {
        val oldCards = cardAdapter!!.getItems()
        val newCards = oldCards.toMutableList().apply {
            saveCard(card)
        }

        val cardDiffUtilCallback = CardDiffUtilCallback(oldCards, newCards)
        val diffResult = DiffUtil.calculateDiff(cardDiffUtilCallback)

        cardAdapter!!.swapData(newCards, false)
        diffResult.dispatchUpdatesTo(cardAdapter!!)
    }

    private fun MutableList<Card>.saveCard(card: Card) {
        indexOfFirst { it.id == card.id }
            .takeUnless { it == -1 }?.let {
                set(it, card)
            } ?: add(card)
    }

    override fun onDestroyView() {
        saveCardSetToActivityStorage()
        cardAdapter = null
        super.onDestroyView()
    }

    override fun onDetach() {
        removeFromActivityStorage(CARD_SET_STORAGE_KEY)
        super.onDetach()
    }

    private fun saveCardSetToActivityStorage() {
        val cards = cardAdapter!!.getItems()
        val cardMap = mutableMapOf<Long, Card>()
        for (card in cards) {
            cardMap[card.id] = card
        }
        writeToActivityStorage(CARD_SET_STORAGE_KEY, getCardSetArg().copy(cards = cardMap))
    }

    override fun onResult(resultCode: Int, data: Any?) {
        Timber.d("on card saving result: $data")
        presenter.onCardSaved(data as Card)
    }

    companion object {

        private const val CARD_SET_KEY = "CARD_SET_KEY"
        private const val CARD_SET_STORAGE_KEY = "CARD_SET_STORAGE_KEY"

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